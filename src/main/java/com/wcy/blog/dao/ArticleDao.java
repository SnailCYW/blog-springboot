package com.wcy.blog.dao;

import com.wcy.blog.dto.*;
import com.wcy.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.ArticleVo;
import com.wcy.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_article】的数据库操作Mapper
* @createDate 2022-09-25 22:58:52
* @Entity com.wcy.blog.entity.Article
*/
public interface ArticleDao extends BaseMapper<Article> {


    Integer articleBackCount(@Param("condition") ConditionVo condition);

    List<ArticleBackDTO> listArticleBack(@Param("current") Long current,
                                         @Param("size") Long size,
                                         @Param("condition") ConditionVo condition);

    ArticleVo getArticleBackById(@Param("articleId") Integer articleId);

    List<ArticleHomeDTO> listHomeArticles(@Param("current") Long current,
                                          @Param("size") Long size);

    List<ArticlePreviewDTO> listArticleByCondition(@Param("current") Long current,
                                                   @Param("size") Long size,
                                                   @Param("condition") ConditionVo condition);

    ArticleDTO getArticleById(@Param("articleId") Integer articleId);

    List<ArticleRecommendDTO> selectRecommendArticles(@Param("articleId") Integer articleId);

    List<ArticleStatisticsDTO> listArticleStatistics();
}




