package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/16:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "文章")
public class ArticleVo {

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(name = "articleContent", value = "文章内容", required = true, dataType = "String")
    private String articleContent;

    @ApiModelProperty(name = "articleCover", value = "文章缩略图", dataType = "String")
    private String articleCover;

    @NotBlank(message = "文章标题不能为空")
    @ApiModelProperty(name = "articleTitle", value = "文章标题", required = true, dataType = "String")
    private String articleTitle;

    @ApiModelProperty(name = "categoryName", value = "文章分类", dataType = "String")
    private String categoryName;

    @ApiModelProperty(name = "id", value = "文章id", dataType = "Integer")
    private Integer id;

    @ApiModelProperty(name = "isTop", value = "是否置顶", dataType = "Integer")
    private Integer isTop;

    @ApiModelProperty(name = "originalUrl", value = "原文链接", dataType = "String")
    private String originalUrl;

    @ApiModelProperty(name = "status", value = "文章状态", dataType = "Integer")
    private Integer status;

    @ApiModelProperty(name = "tagNameList", value = "文章标签", dataType = "List<String>")
    private List<String> tagNameList;

    @ApiModelProperty(name = "type", value = "文章类型", dataType = "Integer")
    private Integer type;

}
