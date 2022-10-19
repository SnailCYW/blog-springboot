package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/21:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAreaDTO {

    private String name;

    private Integer value;

}
