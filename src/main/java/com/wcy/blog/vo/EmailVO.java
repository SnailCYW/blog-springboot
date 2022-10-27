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
 * @Date: 2022/10/27/9:44
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "绑定邮箱")
public class EmailVO {

    @ApiModelProperty(name = "nickname", value = "邮箱验证码", dataType = "String")
    private String code;

    @ApiModelProperty(name = "nickname", value = "用户名", dataType = "String")
    private String email;

}
