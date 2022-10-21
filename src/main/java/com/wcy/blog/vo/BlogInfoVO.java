package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/21/9:05
 */
@Data
@ApiModel(description = "博客信息")
public class BlogInfoVO {

    @NotBlank
    @ApiModelProperty(value = "aboutContent", name = "关于我内容", required = true, dataType = "String")
    private String aboutContent;

}
