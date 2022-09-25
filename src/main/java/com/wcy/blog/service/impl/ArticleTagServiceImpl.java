package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.ArticleTag;
import com.wcy.blog.service.ArticleTagService;
import com.wcy.blog.dao.ArticleTagDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_article_tag】的数据库操作Service实现
* @createDate 2022-09-25 22:59:17
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag>
    implements ArticleTagService{

}




