package com.wcy.blog.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/20:36
 */

@Data
public class UserBackDTO {

    private String avatar;

    private LocalDateTime createTime;

    private Integer id;

    private String ipAddress;

    private String ipSource;

    private Integer isDisable;

    private LocalDateTime lastLoginTime;

    private Integer loginType;

    private String nickname;

    private List<UserRoleDTO> roleList;

    private Integer status;

    private Integer userInfoId;

}
