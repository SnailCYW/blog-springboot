package com.wcy.blog.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wcy.blog.dao.UserAuthDao;
import com.wcy.blog.dao.UserInfoDao;
import com.wcy.blog.dao.UserRoleDao;
import com.wcy.blog.dto.SocialTokenDTO;
import com.wcy.blog.dto.SocialUserInfoDTO;
import com.wcy.blog.dto.UserDetailDTO;
import com.wcy.blog.dto.UserInfoDTO;
import com.wcy.blog.entity.UserAuth;
import com.wcy.blog.entity.UserInfo;
import com.wcy.blog.entity.UserRole;
import com.wcy.blog.enums.RoleEnum;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.service.impl.UserDetailsServiceImpl;
import com.wcy.blog.strategy.SocialLoginStrategy;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

import static com.wcy.blog.constant.CommonConst.TRUE;
import static com.wcy.blog.enums.ZoneEnum.SHANGHAI;

/**
 * 第三方登录抽象模板
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/14:41
 */
@Service
public abstract class AbstractSocialLoginStrategyImpl implements SocialLoginStrategy {

    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserInfoDTO login(String data) {
        // 创建登录信息
        UserDetailDTO userDetailDTO;
        // 获取第三方token信息
        SocialTokenDTO socialToken = getSocialToken(data);
        // 获取用户ip信息
        String ipAddress = IpUtils.getIpAddress(request);
        String ipSource = IpUtils.getIpSource(ipAddress);
        // 判断是否已注册
        UserAuth user = getUserAuth(socialToken);
        if (Objects.nonNull(user)) {
            // 返回数据库用户信息
            userDetailDTO = getUserDetail(user, ipAddress, ipSource);
        } else {
            // 获取第三方用户信息，保存到数据库返回
            userDetailDTO = saveUserDetail(socialToken, ipAddress, ipSource);
        }
        // 判断账号是否禁用
        if (userDetailDTO.getIsDisable().equals(TRUE)) {
            throw new BizException("账号已被禁用");
        }
        // 将登录信息放入springSecurity管理
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetailDTO, null, userDetailDTO.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // 返回用户信息
        return BeanCopyUtils.copyObject(userDetailDTO, UserInfoDTO.class);
    }

    private UserDetailDTO saveUserDetail(SocialTokenDTO socialToken, String ipAddress, String ipSource) {
        // 获取第三方用户信息
        SocialUserInfoDTO socialUserInfo = getSocialUserInfo(socialToken);
        // 保存用户信息
        UserInfo userInfo = UserInfo.builder()
                .nickname(socialUserInfo.getNickname())
                .avatar(socialUserInfo.getAvatar())
                .build();
        userInfoDao.insert(userInfo);
        // 保存账号信息
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(socialToken.getOpenId())
                .password(socialToken.getAccessToken())
                .loginType(socialToken.getLoginType())
                .lastLoginTime(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())))
                .ipAddress(ipAddress)
                .ipSource(ipSource)
                .build();
        userAuthDao.insert(userAuth);
        // 绑定角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);
        return userDetailsService.convertUserDetail(userAuth, request);
    }

    protected abstract SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialToken);

    private UserDetailDTO getUserDetail(UserAuth user, String ipAddress, String ipSource) {
        userAuthDao.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getLastLoginTime, LocalDateTime.now())
                .set(UserAuth::getIpAddress, ipAddress)
                .set(UserAuth::getIpSource, ipSource)
                .eq(UserAuth::getId, user.getId()));
        return userDetailsService.convertUserDetail(user, request);
    }

    /**
     * 获取用户账号
     *
     * @return {@link UserAuth} 用户账号
     */
    private UserAuth getUserAuth(SocialTokenDTO socialToken) {
        return userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername, socialToken.getOpenId())
                .eq(UserAuth::getLoginType, socialToken.getLoginType()));
    }

    protected abstract SocialTokenDTO getSocialToken(String data);
}
