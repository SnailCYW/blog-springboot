package com.wcy.blog.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/18:13
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "逻辑删除")
public class DeleteVO {

    @ApiModelProperty(name = "idList", value = "id列表", required = true, dataType = "List<Integer>")
    private List<Integer> idList;

    @ApiModelProperty(name = "isDelete", value = "删除状态", required = true, dataType = "Integer")
    private Integer isDelete;

}
