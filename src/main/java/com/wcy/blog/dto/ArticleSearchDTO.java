package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/11:20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearchDTO {

    private String articleContent;

    private String articleTitle;

    private Integer id;

    private Integer isDelete;

    private Integer status;

}
