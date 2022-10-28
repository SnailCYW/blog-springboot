package com.wcy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dao.ArticleDao;
import com.wcy.blog.dao.TalkDao;
import com.wcy.blog.dao.UserInfoDao;
import com.wcy.blog.dto.*;
import com.wcy.blog.entity.Comment;
import com.wcy.blog.service.BlogInfoService;
import com.wcy.blog.service.CommentService;
import com.wcy.blog.dao.CommentDao;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.HTMLUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.util.UserUtils;
import com.wcy.blog.vo.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.CommonConst.*;
import static com.wcy.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.wcy.blog.constant.RedisPrefixConst.COMMENT_LIKE_COUNT;
import static com.wcy.blog.constant.RedisPrefixConst.COMMENT_USER_LIKE;
import static com.wcy.blog.enums.CommentTypeEnum.*;

/**
 * @author Snail
 * @description 针对表【tb_comment】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment> implements CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private TalkDao talkDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private BlogInfoService blogInfoService;

    /**
     * 网站网址
     */
    @Value("${website.url}")
    private String websiteUrl;

    @Override
    public PageResult<CommentBackDTO> listCommentBack(ConditionVO condition) {
        Integer count = commentDao.getCountBack(condition);
        List<CommentBackDTO> commentBackDTOList = commentDao.listCommentBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(count, commentBackDTOList);
    }

    @Override
    public void deleteComment(List<Integer> commentIdList) {
        commentDao.deleteBatchIds(commentIdList);
    }

    @Override
    public void reviewComment(ReviewVO reviewVO) {
        commentDao.update(new Comment(), new LambdaUpdateWrapper<Comment>()
                .set(Comment::getIsReview, reviewVO.getIsReview())
                .in(Comment::getId, reviewVO.getIdList()));
    }

    @Override
    public PageResult<CommentDTO> listComment(CommentVO commentVO) {
        // 查询评论量
        Integer commentCount = commentDao.selectCount(new LambdaQueryWrapper<Comment>()
                .eq(Objects.nonNull(commentVO.getTopicId()), Comment::getTopicId, commentVO.getTopicId())
                .isNull(Comment::getParentId)
                .eq(Comment::getType, commentVO.getType())
                .eq(Comment::getIsReview, TRUE));
        if (commentCount == 0) {
            return new PageResult<>();
        }
        // 分页查询评论数据
        List<CommentDTO> commentDTOList = commentDao.listComments(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentVO);
        if (CollectionUtils.isEmpty(commentDTOList)) {
            return new PageResult<>();
        }
        // 提取评论id集合
        List<Integer> commentIdList = commentDTOList.stream().map(CommentDTO::getId).collect(Collectors.toList());
        // 根据评论id集合查询子评论
        List<ReplyDTO> replyDTOList = commentDao.listReplies(commentIdList);
        // 查询redis的评论点赞数据
        Map<String, Object> likeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        //封装回复点赞量
        replyDTOList.forEach(item -> item.setLikeCount((Integer) likeCountMap.get(item.getId().toString())));
        // 根据评论id分组回复数据
        Map<Integer, List<ReplyDTO>> replayMap = replyDTOList.stream().collect(Collectors.groupingBy(ReplyDTO::getParentId));
        // 根据评论id查询回复量
        List<ReplyCountDTO> replyCountDTOList = commentDao.listReplyCountByCommentId(commentIdList);
        Map<Integer, Integer> replyCountMap = replyCountDTOList.stream().collect(Collectors.toMap(ReplyCountDTO::getCommentId, ReplyCountDTO::getReplyCount));
        // 封装评论数据
        commentDTOList.forEach(item -> {
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setReplyDTOList(replayMap.get(item.getId()));
            item.setReplyCount(replyCountMap.get(item.getId()));
        });
        return new PageResult<>(commentCount, commentDTOList);
    }

    @Override
    public void addComment(CommentVO commentVO) {
        // 判断是否需要审核
        WebsiteConfigVO websiteConfig = blogInfoService.getWebsiteConfig();
        Integer isReview = websiteConfig.getIsCommentReview();
        // 过滤标签
        commentVO.setCommentContent(HTMLUtils.filter(commentVO.getCommentContent()));
        Comment comment = Comment.builder()
                .userId(UserUtils.getLoginUser().getUserInfoId())
                .replyUserId(commentVO.getReplyUserId())
                .topicId(commentVO.getTopicId())
                .commentContent(commentVO.getCommentContent())
                .parentId(commentVO.getParentId())
                .type(commentVO.getType())
                .isReview(isReview == TRUE ? FALSE : TRUE)
                .build();
        commentDao.insert(comment);
        // 判断是否开启邮箱通知,通知用户
        if (websiteConfig.getIsEmailNotice().equals(TRUE)) {
            CompletableFuture.runAsync(() -> notice(comment));
        }
    }

    @Override
    public void likeComment(Integer commentId) {
        String commentUserLikeKey = COMMENT_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisService.sIsMember(commentUserLikeKey, commentId)) {
            redisService.sRemove(commentUserLikeKey, commentId);
            redisService.hDecr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        } else {
            redisService.sAdd(commentUserLikeKey, commentId);
            redisService.hIncr(COMMENT_LIKE_COUNT, commentId.toString(), 1L);
        }
    }

    @Override
    public List<ReplyDTO> listCommentByParentId(Integer commentId) {
        List<ReplyDTO> replyDTOList = commentDao.listAllRepliesByCommentId(PageUtils.getLimitCurrent(), PageUtils.getSize(), commentId);
        Map<String, Object> commentLikeCountMap = redisService.hGetAll(COMMENT_LIKE_COUNT);
        replyDTOList.forEach(item -> item.setLikeCount((Integer) commentLikeCountMap.get(item.getId().toString())));
        return replyDTOList;
    }

    /**
     * 通知评论用户
     *
     * @param comment 评论信息
     */
    public void notice(Comment comment) {
        // 查询回复用户邮箱号
        Integer userId = BLOGGER_ID;
        String id = Objects.nonNull(comment.getTopicId()) ? comment.getTopicId().toString() : "";
        if (Objects.nonNull(comment.getReplyUserId())) {
            userId = comment.getReplyUserId();
        } else {
            switch (Objects.requireNonNull(getCommentEnum(comment.getType()))) {
                case ARTICLE:
                    userId = articleDao.selectById(comment.getTopicId()).getUserId();
                    break;
                case TALK:
                    userId = talkDao.selectById(comment.getTopicId()).getUserId();
                    break;
                default:
                    break;
            }
        }
        String email = userInfoDao.selectById(userId).getEmail();
        if (StringUtils.isNotBlank(email)) {
            // 发送消息
            EmailDTO emailDTO = new EmailDTO();
            if (comment.getIsReview().equals(TRUE)) {
                // 评论提醒
                emailDTO.setEmail(email);
                emailDTO.setSubject("评论提醒");
                // 获取评论路径
                String url = websiteUrl + getCommentPath(comment.getType()) + id;
                emailDTO.setContent("您收到了一条新的回复，请前往" + url + "\n页面查看");
            } else {
                // 管理员审核提醒
                String adminEmail = userInfoDao.selectById(BLOGGER_ID).getEmail();
                emailDTO.setEmail(adminEmail);
                emailDTO.setSubject("审核提醒");
                emailDTO.setContent("您收到了一条新的回复，请前往后台管理页面审核");
            }
            rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        }
    }

}




