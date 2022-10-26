package com.wcy.blog.service;

import com.wcy.blog.dto.MessageBackDTO;
import com.wcy.blog.dto.MessageDTO;
import com.wcy.blog.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.MessageVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.ReviewVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_message】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface MessageService extends IService<Message> {

    PageResult<MessageBackDTO> listMessagesBack(ConditionVO condition);

    void deleteMessages(List<Integer> messageIdList);

    void reviewMessages(ReviewVO reviewVO);

    List<MessageDTO> listMessages();

    void addMessage(MessageVO messageVO);
}
