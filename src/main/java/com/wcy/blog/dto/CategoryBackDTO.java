package com.wcy.blog.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/17:42
 */
@Data
public class CategoryBackDTO {

    private Integer articleCount;

    private String categoryName;

    private String createTime;

    private Integer id;

}
