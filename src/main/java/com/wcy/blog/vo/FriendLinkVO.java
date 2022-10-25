package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/22:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "友链")
public class FriendLinkVO {

    /**
     *
     */
    @ApiModelProperty(name = "id", value = "友链id", dataType = "Integer")
    private Integer id;

    /**
     * 链接名
     */
    @ApiModelProperty(name = "linkName", value = "友链名", required = true, dataType = "String")
    private String linkName;

    /**
     * 链接头像
     */
    @ApiModelProperty(name = "linkAvatar", value = "友链头像", required = true, dataType = "String")
    private String linkAvatar;

    /**
     * 链接地址
     */
    @ApiModelProperty(name = "linkAddress", value = "友链地址", required = true, dataType = "String")
    private String linkAddress;

    /**
     * 链接介绍
     */
    @ApiModelProperty(name = "linkIntro", value = "友链介绍", required = true, dataType = "String")
    private String linkIntro;

}
