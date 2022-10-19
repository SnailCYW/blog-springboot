package com.wcy.blog.service;

import com.wcy.blog.dto.*;
import com.wcy.blog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ArticleVo;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_article】的数据库操作Service
* @createDate 2022-09-25 22:59:17
*/
public interface ArticleService extends IService<Article> {

    PageResult<ArticleBackDTO> listArticleBack(ConditionVo condition);

    ArticleVo getArticleBackById(Integer articleId);

    List<ArticleHomeDTO> listHomeArticles();

    PageResult<ArchiveDTO> listArchives();

    ArticlePreviewListDTO listArticleByCondition(ConditionVo condition);

    ArticleDTO getArticleById(Integer articleId);

    List<ArticleSearchDTO> searchArticles(ConditionVo condition);

    void addOrUpdateArticle(ArticleVo articleVo);
}
