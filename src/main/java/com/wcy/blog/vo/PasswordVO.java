package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/9:09
 */
@Data
@ApiModel(description = "密码")
public class PasswordVO {

    @NotBlank
    @ApiModelProperty(name = "newPassword", value = "新密码", required = true, dataType = "String")
    private String newPassword;

    @NotBlank
    @ApiModelProperty(name = "oldPassword", value = "旧密码", required = true, dataType = "String")
    private String oldPassword;

}
