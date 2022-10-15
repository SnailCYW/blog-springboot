package com.wcy.blog.dto;

import lombok.Data;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/20:01
 */
@Data
public class CategoryDTO {

    private Integer articleCount;

    private String categoryName;

    private Integer id;

}
