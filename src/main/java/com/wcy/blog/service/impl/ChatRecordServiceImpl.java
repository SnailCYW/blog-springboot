package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.ChatRecord;
import com.wcy.blog.service.ChatRecordService;
import com.wcy.blog.dao.ChatRecordDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_chat_record】的数据库操作Service实现
* @createDate 2022-09-25 22:59:17
*/
@Service
public class ChatRecordServiceImpl extends ServiceImpl<ChatRecordDao, ChatRecord>
    implements ChatRecordService{

}




