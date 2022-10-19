package com.wcy.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/19:41
 */
@Data
public class ArticlePreviewDTO {

    private String articleCover;

    private String articleTitle;

    private Integer categoryId;

    private String categoryName;

    private String createTime;

    private Integer id;

    private List<TagDTO> tagDTOList;

}
