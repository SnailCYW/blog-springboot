package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/19:45
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePreviewListDTO {

    private List<ArticlePreviewDTO> articlePreviewDTO;

    private String name;

}
