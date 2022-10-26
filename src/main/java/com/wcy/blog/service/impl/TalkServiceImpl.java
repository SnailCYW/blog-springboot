package com.wcy.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dao.CommentDao;
import com.wcy.blog.dto.CommentCountDTO;
import com.wcy.blog.dto.TalkBackDTO;
import com.wcy.blog.dto.TalkDTO;
import com.wcy.blog.entity.Talk;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.service.TalkService;
import com.wcy.blog.dao.TalkDao;
import com.wcy.blog.strategy.context.UploadStrategyContext;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.CommonUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.util.UserUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.TalkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.RedisPrefixConst.TALK_LIKE_COUNT;
import static com.wcy.blog.constant.RedisPrefixConst.TALK_USER_LIKE;
import static com.wcy.blog.enums.ArticleStatusEnum.PUBLIC;
import static com.wcy.blog.enums.FilePathEnum.TALK;

/**
 * @author Snail
 * @description 针对表【tb_talk】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkDao, Talk>
        implements TalkService {

    @Autowired
    private TalkDao talkDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Override
    public PageResult<TalkBackDTO> listTalksBack(ConditionVO condition) {
        Integer count = talkDao.selectCount(new LambdaQueryWrapper<Talk>()
                .eq(Objects.nonNull(condition.getStatus()), Talk::getStatus, condition.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        List<TalkBackDTO> talkBackDTOList = talkDao.listTalksBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        talkBackDTOList.forEach(item -> {
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResult<>(count, talkBackDTOList);
    }

    @Override
    public void addOrUpdateTalk(TalkVO talkVO) {
        Talk talk = BeanCopyUtils.copyObject(talkVO, Talk.class);
        talk.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(talk);
    }

    @Override
    public void deleteTalks(List<Integer> talkIdList) {
        talkDao.deleteBatchIds(talkIdList);
    }

    @Override
    public String uploadTalkImages(MultipartFile file) {
        return uploadStrategyContext.executeUploadStrategy(file, TALK.getPath());
    }

    @Override
    public TalkBackDTO listTalksBackById(Integer talkId) {
        TalkBackDTO talkBackDTO = talkDao.listTalksBackById(talkId);
        talkBackDTO.setImgList(CommonUtils.castList(JSON.parseObject(talkBackDTO.getImages(), List.class), String.class));
        return talkBackDTO;
    }

    @Override
    public List<String> listHomeTalks() {
        List<Talk> talkList = talkDao.selectList(new LambdaQueryWrapper<Talk>()
                .select(Talk::getContent)
                .eq(Talk::getStatus, PUBLIC.getStatus()));
        List<String> stringList = talkList.stream().map(Talk::getContent).collect(Collectors.toList());
        return stringList;
    }

    @Override
    public PageResult<TalkDTO> listTalks() {
        Integer count = talkDao.selectCount(new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus, PUBLIC.getStatus()));
        if (count == 0) {
            return new PageResult<>();
        }
        List<TalkDTO> talkBackDTOList = talkDao.listTalks(PageUtils.getLimitCurrent(), PageUtils.getSize());
        Map<String, Object> likeCountMap = redisService.hGetAll(TALK_LIKE_COUNT);
        List<Integer> talkIdList = talkBackDTOList.stream().map(TalkDTO::getId).collect(Collectors.toList());
        Map<Integer, Integer> commentCountMap = commentDao.listCommentCountByTopicIds(talkIdList)
                .stream()
                .collect(Collectors.toMap(CommentCountDTO::getId, CommentCountDTO::getCommentCount));
        talkBackDTOList.forEach(item -> {
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
            item.setCommentCount(commentCountMap.get(item.getId()));
        });
        return new PageResult<>(count, talkBackDTOList);
    }

    @Override
    public TalkDTO getTalkById(Integer talkId) {
        TalkDTO talkBackDTO = talkDao.listTalkById(talkId);
        Map<String, Object> likeCountMap = redisService.hGetAll(TALK_LIKE_COUNT);
        Integer commentCount = commentDao.listCommentCountByTopicId(talkId);
        if (Objects.nonNull(talkBackDTO.getImages())) {
            talkBackDTO.setImgList(CommonUtils.castList(JSON.parseObject(talkBackDTO.getImages(), List.class), String.class));
        }
        talkBackDTO.setLikeCount((Integer) likeCountMap.get(talkBackDTO.getId().toString()));
        talkBackDTO.setCommentCount(commentCount);
        return talkBackDTO;
    }

    @Override
    public void likeTalk(Integer talkId) {
        String talkLikeKey = TALK_USER_LIKE + UserUtils.getLoginUser().getUserInfoId();
        if (redisService.sIsMember(talkLikeKey, talkId)) {
            redisService.sRemove(talkLikeKey, talkId);
            redisService.hDecr(TALK_LIKE_COUNT, talkId.toString(), 1L);
        } else {
            redisService.sAdd(talkLikeKey, talkId);
            redisService.hIncr(TALK_LIKE_COUNT, talkId.toString(), 1L);
        }
    }
}

