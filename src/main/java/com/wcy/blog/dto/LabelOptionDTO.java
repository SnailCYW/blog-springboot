package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/21/19:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelOptionDTO {

    private List<LabelOptionDTO> children;

    private Integer id;

    private String label;

}
