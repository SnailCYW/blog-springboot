package com.wcy.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/17:11
 */
@Data
public class ArticleHomeDTO {

    private String articleContent;

    private String articleCover;

    private String articleTitle;

    private Integer categoryId;

    private String categoryName;

    private String createTime;

    private Integer id;

    private Integer isTop;

    private List<TagDTO> tagDTOList;

    private Integer type;

}
