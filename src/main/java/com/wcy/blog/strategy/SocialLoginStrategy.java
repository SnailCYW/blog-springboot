package com.wcy.blog.strategy;

import com.wcy.blog.dto.UserInfoDTO;
import com.wcy.blog.enums.LoginTypeEnum;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/14:37
 */
public interface SocialLoginStrategy {

    public UserInfoDTO login(String data);

}
