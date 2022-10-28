package com.wcy.blog.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wcy.blog.entity.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/27/12:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceDTO {

    private List<ResourceDTO> children;

    private LocalDateTime createTime;

    private Integer id;

    private Integer isAnonymous;

    private Integer isDisable;

    private String requestMethod;

    private String resourceName;

    private String url;

}
