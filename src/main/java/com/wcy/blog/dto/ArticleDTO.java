package com.wcy.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/20:42
 */
@Data
public class ArticleDTO {

    private String articleContent;

    private String articleCover;

    private String articleTitle;

    private Integer categoryId;

    private String categoryName;

    private LocalDateTime createTime;

    private Integer id;

    private ArticlePaginationDTO lastArticle;

    private Integer likeCount;

    private List<ArticleRecommendDTO> newestArticleList;

    private ArticlePaginationDTO nextArticle;

    private String originalUrl;

    private List<ArticleRecommendDTO> recommendArticleList;

    private List<TagDTO> tagDTOList;

    private Integer type;

    private LocalDateTime updateTime;

    private Integer viewsCount;

}
