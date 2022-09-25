package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Message;
import com.wcy.blog.service.MessageService;
import com.wcy.blog.dao.MessageDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_message】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message>
    implements MessageService{

}




