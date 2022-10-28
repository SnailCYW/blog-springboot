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
 * @Date: 2022/10/28/11:00
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "资源")
public class ResourceVO {

    @ApiModelProperty(name = "资源id", value = "文章id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "是否匿名访问", value = "文章id", dataType = "Integer")
    private Integer isAnonymous;

    @ApiModelProperty(name = "父资源id", value = "文章id", dataType = "Integer")
    private Integer parentId;

    @ApiModelProperty(name = "请求方式", value = "文章id", dataType = "String")
    private String requestMethod;

    @ApiModelProperty(name = "资源名", value = "文章id", dataType = "String")
    private String resourceName;

    @ApiModelProperty(name = "资源路径", value = "文章id", dataType = "String")
    private String url;

}
