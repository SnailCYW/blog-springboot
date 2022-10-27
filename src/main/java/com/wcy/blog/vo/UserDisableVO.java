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
 * @Date: 2022/10/27/8:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户禁用状态")
public class UserDisableVO {

    @ApiModelProperty(name = "id", value = "用户信息id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "isDisable", value = "是否禁用", dataType = "Integer")
    private Integer isDisable;

}
