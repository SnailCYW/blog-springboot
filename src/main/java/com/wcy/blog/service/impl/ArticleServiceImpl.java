package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Article;
import com.wcy.blog.service.ArticleService;
import com.wcy.blog.dao.ArticleDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_article】的数据库操作Service实现
* @createDate 2022-09-25 22:59:17
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article>
    implements ArticleService{

}




