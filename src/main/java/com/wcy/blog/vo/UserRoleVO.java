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
 * @Date: 2022/10/27/8:54
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户权限")
public class UserRoleVO {

    @ApiModelProperty(name = "nickname", value = "昵称", dataType = "String")
    private String nickname;

    @ApiModelProperty(name = "roleIdList", value = "角色id集合", dataType = "List<Integer>")
    private List<Integer> roleIdList;

    @ApiModelProperty(name = "userInfoId", value = "用户信息id", dataType = "Integer")
    private Integer userInfoId;

}
