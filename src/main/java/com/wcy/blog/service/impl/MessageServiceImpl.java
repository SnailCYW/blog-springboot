package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.MessageBackDTO;
import com.wcy.blog.dto.MessageDTO;
import com.wcy.blog.entity.Message;
import com.wcy.blog.service.MessageService;
import com.wcy.blog.dao.MessageDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.IpUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.MessageVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.wcy.blog.constant.CommonConst.TRUE;

/**
 * @author Snail
 * @description 针对表【tb_message】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message>
        implements MessageService {

    @Autowired
    private MessageDao messageDao;
    @Autowired
    private HttpServletRequest request;

    @Override
    public PageResult<MessageBackDTO> listMessagesBack(ConditionVO condition) {
        Page<Message> page = new Page<>(PageUtils.getLimitCurrent(), PageUtils.getSize());
        Page<Message> messagePage = messageDao.selectPage(page, new LambdaQueryWrapper<Message>()
                .like(Objects.nonNull(condition.getKeywords()), Message::getNickname, condition.getKeywords())
                .eq(Objects.nonNull(condition.getIsReview()), Message::getIsReview, condition.getIsReview())
                .orderByDesc(Message::getId));
        return new PageResult<>((int) messagePage.getTotal(), BeanCopyUtils.copyList(messagePage.getRecords(), MessageBackDTO.class));
    }

    @Override
    public void deleteMessages(List<Integer> messageIdList) {
        messageDao.deleteBatchIds(messageIdList);
    }

    @Override
    public void reviewMessages(ReviewVO reviewVO) {
        messageDao.update(new Message(), new LambdaUpdateWrapper<Message>()
                .set(Message::getIsReview, reviewVO.getIsReview())
                .in(Message::getId, reviewVO.getIdList()));
    }

    @Override
    public List<MessageDTO> listMessages() {
        List<Message> messageList = messageDao.selectList(new LambdaQueryWrapper<Message>().eq(Message::getIsReview, TRUE));
        return BeanCopyUtils.copyList(messageList, MessageDTO.class);
    }

    @Override
    public void addMessage(MessageVO messageVO) {
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        Message message = Message.builder()
                .avatar(messageVO.getAvatar())
                .messageContent(messageVO.getMessageContent())
                .nickname(messageVO.getNickname())
                .time(messageVO.getTime())
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .build();
        messageDao.insert(message);
    }
}




