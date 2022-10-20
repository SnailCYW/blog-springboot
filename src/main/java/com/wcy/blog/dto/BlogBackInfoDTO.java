package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/21:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogBackInfoDTO {

    private Integer articleCount;

    private List<ArticleRankDTO> articleRankDTOList;

    private List<ArticleStatisticsDTO> articleStatisticsList;

    private List<CategoryDTO> categoryDTOList;

    private Integer messageCount;

    private List<TagDTO> tagDTOList;

    private List<UniqueViewDTO> uniqueViewDTOList;

    private Integer userCount;

    private Integer viewsCount;

}
