package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/21/11:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDTO {

    private List<MenuDTO> children;

    private String component;

    private LocalDateTime createTime;

    private String icon;

    private Integer id;

    private Integer idDisable;

    private Integer isHidden;

    private String name;

    private Integer orderNum;

    private String path;


}
