package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/08/17:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "分页对象")
public class PageResult<T> {

    @ApiModelProperty(name = "count", value = "总数", required = true, dataType = "Integer")
    private Integer count;

    @ApiModelProperty(name = "recordList", value = "分页列表", required = true, dataType = "List<T>")
    private List<T> recordList;


    public PageResult(java.lang.Integer p0) {
    }
}
