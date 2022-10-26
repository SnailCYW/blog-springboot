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
 * @Date: 2022/10/26/19:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "说说对象")
public class TalkVO {

    @ApiModelProperty(name = "content", value = "说说内容", dataType = "String")
    private String content;

    @ApiModelProperty(name = "id", value = "说说id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "images", value = "说说图片", dataType = "String")
    private String images;

    @ApiModelProperty(name = "isTop", value = "置顶状态", dataType = "Integer")
    private Integer isTop;

    @ApiModelProperty(name = "status", value = "说说状态", dataType = "Integer")
    private Integer status;

}
