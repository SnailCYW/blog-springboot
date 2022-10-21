package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/21/17:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "菜单")
public class MenuVO {

    @ApiModelProperty(name = "component", value = "组件", dataType = "String")
    private String component;

    @ApiModelProperty(name = "icon", value = "菜单icon", dataType = "String")
    private String icon;

    @ApiModelProperty(name = "id", value = "菜单id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "isHidden", value = "是否隐藏", dataType = "Integer")
    private Integer isHidden;

    @ApiModelProperty(name = "name", value = "菜单名", dataType = "String")
    private String name;

    @ApiModelProperty(name = "orderNum", value = "排序", dataType = "Integer")
    private Integer orderNum;

    @ApiModelProperty(name = "parentId", value = "父id", dataType = "Integer")
    private Integer parentId;

    @ApiModelProperty(name = "path", value = "路径", dataType = "String")
    private String path;

}
