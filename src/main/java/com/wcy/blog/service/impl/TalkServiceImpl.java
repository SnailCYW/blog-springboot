package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Talk;
import com.wcy.blog.service.TalkService;
import com.wcy.blog.dao.TalkDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_talk】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class TalkServiceImpl extends ServiceImpl<TalkDao, Talk>
    implements TalkService{

}




