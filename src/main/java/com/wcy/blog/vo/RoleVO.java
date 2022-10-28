package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/28/12:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "角色")
public class RoleVO {

    @ApiModelProperty(name = "id", value = "用户id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "menuIdList", value = "菜单列表", required = true, dataType = "List<Integer>")
    private List<Integer> menuIdList;

    @ApiModelProperty(name = "resourceIdList", value = "资源列表", required = true, dataType = "List<Integer>")
    private List<Integer> resourceIdList;

    @ApiModelProperty(name = "roleLabel", value = "标签名", required = true, dataType = "String")
    private String roleLabel;

    @ApiModelProperty(name = "roleName", value = "角色名", required = true, dataType = "String")
    private String roleName;

}
