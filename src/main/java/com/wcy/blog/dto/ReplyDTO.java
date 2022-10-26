package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/25/15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyDTO {

    private String avatar;

    private String commentContent;

    private LocalDateTime createTime;

    private Integer id;

    private Integer likeCount;

    private String nickname;

    private Integer parentId;

    private String replyNickname;

    private Integer replyUserId;

    private String replyWebSite;

    private Integer userId;

    private String webSite;
}
