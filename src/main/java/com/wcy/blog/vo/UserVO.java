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
 * @Date: 2022/10/22/10:51
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户账号")
public class UserVO {

    @ApiModelProperty(value = "code", name = "邮箱验证码", required = true, dataType = "String")
    private String code;

    @ApiModelProperty(value = "username", name = "用户名", required = true, dataType = "String")
    private String username;

    @ApiModelProperty(value = "password", name = "密码", required = true, dataType = "String")
    private String password;

}
