package com.wcy.blog.service;

import com.wcy.blog.dto.*;
import com.wcy.blog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_article】的数据库操作Service
* @createDate 2022-09-25 22:59:17
*/
public interface ArticleService extends IService<Article> {

    PageResult<ArticleBackDTO> listArticleBack(ConditionVO condition);

    ArticleVO getArticleBackById(Integer articleId);

    List<ArticleHomeDTO> listHomeArticles();

    PageResult<ArchiveDTO> listArchives();

    ArticlePreviewListDTO listArticleByCondition(ConditionVO condition);

    ArticleDTO getArticleById(Integer articleId);

    List<ArticleSearchDTO> searchArticles(ConditionVO condition);

    void addOrUpdateArticle(ArticleVO articleVo);

    void deleteOrRestoreArticle(DeleteVO deleteVO);

    void deleteArticle(List<Integer> articleIdList);

    List<String> exportArticles(List<Integer> articleIdList);

    String uploadImages(MultipartFile file);

    void importArticles(MultipartFile file, String type);

    void updateArticleWithTop(ArticleTopVO articleTopVO);

    void likeArticle(Integer articleId);
}
