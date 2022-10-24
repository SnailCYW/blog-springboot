package com.wcy.blog.strategy.context;

import com.wcy.blog.dto.UserInfoDTO;
import com.wcy.blog.enums.LoginTypeEnum;
import com.wcy.blog.strategy.SocialLoginStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/14:33
 */
@Service
public class SocialLoginStrategyContext {

    @Autowired
    Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    public UserInfoDTO executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }

}
