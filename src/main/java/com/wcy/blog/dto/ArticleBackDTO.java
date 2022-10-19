package com.wcy.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/17/10:45
 */
@Data
public class ArticleBackDTO {

    private String articleCover;

    private String articleTitle;

    private String categoryName;

    private String createTime;

    private Integer id;

    private Integer isDelete;

    private Integer isTop;
    private Integer likeCount;

    private Integer status;

    private List<TagDTO> tagDTOList;

    private Integer type;

    private Integer viewsCount;

}
