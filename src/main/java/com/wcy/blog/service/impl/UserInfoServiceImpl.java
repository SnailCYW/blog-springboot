package com.wcy.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wcy.blog.dao.UserAuthDao;
import com.wcy.blog.dao.UserRoleDao;
import com.wcy.blog.entity.UserAuth;
import com.wcy.blog.strategy.context.UploadStrategyContext;
import com.wcy.blog.vo.*;
import com.wcy.blog.dto.UserDetailDTO;
import com.wcy.blog.dto.UserOnlineDTO;
import com.wcy.blog.entity.UserInfo;
import com.wcy.blog.dao.UserInfoDao;
import com.wcy.blog.entity.UserRole;
import com.wcy.blog.enums.FilePathEnum;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.service.UserRoleService;

import com.wcy.blog.util.UserUtils;
import com.wcy.blog.vo.ConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.RedisPrefixConst.USER_CODE_KEY;
import static com.wcy.blog.enums.FilePathEnum.AVATAR;
import static com.wcy.blog.util.PageUtils.*;
import static com.wcy.blog.util.PageUtils.getLimitCurrent;

/**
 * @author Snail
 * @description 针对表【tb_user_info】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        if (Objects.nonNull(userDisableVO.getId())) {
            userInfoDao.update(new UserInfo(), new LambdaUpdateWrapper<UserInfo>()
                    .set(Objects.nonNull(userDisableVO.getIsDisable()), UserInfo::getIsDisable, userDisableVO.getIsDisable())
                    .eq(UserInfo::getId, userDisableVO.getId()));
        }
    }

    @Override
    public PageResult<UserOnlineDTO> listOnlineUsers(ConditionVO condition) {
        // 获取security在线session
        List<UserOnlineDTO> userOnlineDTOList = sessionRegistry.getAllPrincipals().stream()
                .filter(item -> sessionRegistry.getAllSessions(item, false).size() > 0)
                .map(item -> JSON.parseObject(JSON.toJSONString(item), UserOnlineDTO.class))
                .filter(item -> StringUtils.isBlank(condition.getKeywords()) || item.getNickname().contains(condition.getKeywords()))
                .sorted(Comparator.comparing(UserOnlineDTO::getLastLoginTime).reversed())
                .collect(Collectors.toList());
        // 执行分页
        int fromIndex = getLimitCurrent().intValue();
        int size = getSize().intValue();
        int toIndex = userOnlineDTOList.size() - fromIndex > size ? fromIndex + size : userOnlineDTOList.size();
        List<UserOnlineDTO> userOnlineList = userOnlineDTOList.subList(fromIndex, toIndex);
        return new PageResult<>(userOnlineDTOList.size(), userOnlineList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        UserInfo userInfo = UserInfo.builder()
                .id(userRoleVO.getUserInfoId())
                .nickname(userRoleVO.getNickname())
                .build();
        userInfoDao.updateById(userInfo);
        userRoleDao.delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getUserInfoId()));
        for (Integer roleId : userRoleVO.getRoleIdList()) {
            userRoleDao.insert(UserRole.builder()
                    .roleId(roleId)
                    .userId(userRoleVO.getUserInfoId())
                    .build());
        }
    }

    @Override
    public void offlineUser(Integer userInfoId) {
        // 获取用户session
        List<Object> userInfoList = sessionRegistry.getAllPrincipals().stream()
                .filter(item -> {
                    UserDetailDTO userDetailDTO = (UserDetailDTO) item;
                    return userDetailDTO.getUserInfoId().equals(userInfoId);
                })
                .collect(Collectors.toList());
        List<SessionInformation> allSessions = new ArrayList<>();
        userInfoList.forEach(item -> allSessions.addAll(sessionRegistry.getAllSessions(item, false)));
        // 注销session
        allSessions.forEach(SessionInformation::expireNow);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String updateUserAvatar(MultipartFile file) {
        return uploadStrategyContext.executeUploadStrategy(file, AVATAR.getPath());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void bindUserEmail(EmailVO emailVO) {
        UserDetailDTO loginUser = UserUtils.getLoginUser();
        if (!emailVO.getCode().equals(redisService.get(USER_CODE_KEY + emailVO.getEmail()).toString())) {
            throw new BizException("验证码错误！");
        }
        UserInfo userInfo = UserInfo.builder()
                .id(loginUser.getUserInfoId())
                .email(emailVO.getEmail())
                .build();
        userInfoDao.updateById(userInfo);
        UserAuth userAuth = UserAuth.builder()
                .id(loginUser.getId())
                .username(emailVO.getEmail())
                .build();
        userAuthDao.updateById(userAuth);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        UserInfo userInfo = UserInfo.builder()
                .id(UserUtils.getLoginUser().getUserInfoId())
                .intro(userInfoVO.getIntro())
                .nickname(userInfoVO.getNickname())
                .webSite(userInfoVO.getWebSite())
                .build();
        userInfoDao.updateById(userInfo);
    }
}




