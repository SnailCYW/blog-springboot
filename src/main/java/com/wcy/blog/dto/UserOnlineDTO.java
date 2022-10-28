package com.wcy.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/27/8:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOnlineDTO {

    private String avatar;

    private String browser;

    private String ipAddress;

    private String ipSource;

    private LocalDateTime lastLoginTime;

    private String nickname;

    private String os;

    private Integer userInfoId;

}
