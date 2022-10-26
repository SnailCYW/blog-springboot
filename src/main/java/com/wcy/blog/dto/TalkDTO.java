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
 * @Date: 2022/10/26/20:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TalkDTO {

    private String avatar;

    private Integer commentCount;

    private String content;

    private LocalDateTime createTime;

    private Integer id;

    private String images;

    private List<String> imgList;

    private Integer isTop;

    private Integer likeCount;

    private String nickname;


}
