package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/21/19:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMenuDTO {

    private List<UserMenuDTO> children;

    private String component;

    private Boolean hidden;

    private String icon;

    private String name;

    private String path;

}
