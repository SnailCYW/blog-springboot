package com.wcy.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/9:23
 */
@Data
public class ArticleRecommendDTO {

    private String articleCover;

    private String articleTitle;

    private LocalDateTime createTime;

    private Integer id;

}
