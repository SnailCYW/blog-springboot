package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Comment;
import com.wcy.blog.service.CommentService;
import com.wcy.blog.dao.CommentDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_comment】的数据库操作Service实现
* @createDate 2022-09-25 22:59:17
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment>
    implements CommentService{

}




