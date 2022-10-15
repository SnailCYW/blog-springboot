package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "分类对象")
public class CategoryVO {

    @NotBlank
    @ApiModelProperty(name = "categoryName", value = "分类名", required = true, dataType = "String")
    private String categoryName;

    @ApiModelProperty(name = "id", value = "分类id", dataType = "Integer")
    private Integer id;

}
