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
 * @Date: 2022/10/25/15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    private String avatar;

    private String commentContent;

    private LocalDateTime createTime;

    private Integer id;

    private Integer likeCount;

    private String nickname;

    private Integer replyCount;

    private List<ReplyDTO> replyDTOList;

    private Integer userId;

    private String webSite;
}
