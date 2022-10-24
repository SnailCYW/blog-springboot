package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * QQ登录
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/14:26
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "qq登录信息")
public class QQLoginVO {

    /**
     * accessToken
     */
    @NotBlank(message = "accessToken不能为空")
    @ApiModelProperty(value = "accessToken", name = "qq accessToken", required = true, dataType = "String")
    private String accessToken;

    /**
     * openId
     */
    @NotBlank(message = "openId不能为空")
    @ApiModelProperty(value = "openId", name = "qq openId", required = true, dataType = "String")
    private String openId;
}
