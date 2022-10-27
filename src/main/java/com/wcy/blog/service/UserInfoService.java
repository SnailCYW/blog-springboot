package com.wcy.blog.service;

import com.wcy.blog.dto.UserOnlineDTO;
import com.wcy.blog.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.*;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Snail
* @description 针对表【tb_user_info】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface UserInfoService extends IService<UserInfo> {

    void updateUserDisable(UserDisableVO userDisableVO);

    PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO condition);

    void updateUserRole(UserRoleVO userRoleVO);

    void offlineUser(Integer userInfoId);

    String updateUserAvatar(MultipartFile file);

    void bindUserEmail(EmailVO emailVO);

    void updateUserInfo(UserInfoVO userInfoVO);
}
