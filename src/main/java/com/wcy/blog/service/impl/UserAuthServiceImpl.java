package com.wcy.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.constant.CommonConst;
import com.wcy.blog.dao.UserAuthDao;
import com.wcy.blog.dao.UserInfoDao;
import com.wcy.blog.dao.UserRoleDao;
import com.wcy.blog.dto.EmailDTO;
import com.wcy.blog.dto.UserAreaDTO;
import com.wcy.blog.dto.UserBackDTO;
import com.wcy.blog.dto.UserInfoDTO;
import com.wcy.blog.entity.UserAuth;
import com.wcy.blog.entity.UserInfo;
import com.wcy.blog.entity.UserRole;
import com.wcy.blog.enums.LoginTypeEnum;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.service.BlogInfoService;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.service.UserAuthService;
import com.wcy.blog.strategy.context.SocialLoginStrategyContext;
import com.wcy.blog.util.IpUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.util.UserUtils;
import com.wcy.blog.vo.*;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.CommonConst.*;
import static com.wcy.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.wcy.blog.constant.RedisPrefixConst.*;
import static com.wcy.blog.enums.RoleEnum.USER;
import static com.wcy.blog.enums.UserAreaTypeEnum.getUserAreaType;
import static com.wcy.blog.util.CommonUtils.checkEmail;
import static com.wcy.blog.util.CommonUtils.getRandomCode;
/**
 * @author Snail
 * @description 针对表【tb_user_auth】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class UserAuthServiceImpl extends ServiceImpl<UserAuthDao, UserAuth>
        implements UserAuthService {

    @Autowired
    private UserAuthDao userAuthDao;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private BlogInfoService blogInfoService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SocialLoginStrategyContext socialLoginStrategyContext;

    @Override
    public PageResult<UserBackDTO> listUsersBack(ConditionVO condition) {
        Integer count = userAuthDao.selectCount(null);
        if (count == 0) {
            return new PageResult<>();
        }
        List<UserBackDTO> userBackDTOList = userAuthDao.listUsersBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(count, userBackDTOList);
    }

    @Override
    public List<UserAreaDTO> getUsersArea(ConditionVO condition) {
        List<UserAreaDTO> userAreaDTOList = new ArrayList<>();
        switch (Objects.requireNonNull(getUserAreaType(condition.getType()))) {
            case USER:
                Object userArea = redisService.get(USER_AREA);
                if (Objects.nonNull(userArea)) {
                    userAreaDTOList = JSON.parseObject(userArea.toString(), List.class);
                }
                return userAreaDTOList;
            case VISITOR:
                Map<String, Object> visitorArea = redisService.hGetAll(VISITOR_AREA);
                if (Objects.nonNull(visitorArea)) {
                    userAreaDTOList = visitorArea.entrySet().stream()
                            .map(item -> UserAreaDTO.builder()
                                    .name(item.getKey())
                                    .value(Long.valueOf(item.getValue().toString()))
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
        }
        return userAreaDTOList;
    }

    /**
     * bcrypt 有三个特点:
     * <p>
     * 每一次 Hash 出来的值不一样。
     * 计算非常缓慢。
     * 每次的 salt 是随机的生成的，不用担心 salt 会泄露。
     *
     * @param passwordVo
     */
    @Override
    public void updateAdminPassword(PasswordVO passwordVo) {
        UserAuth user = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getPassword)
                .eq(UserAuth::getId, UserUtils.getLoginUser().getId()));
        if (Objects.nonNull(user) && BCrypt.checkpw(passwordVo.getOldPassword(), user.getPassword())) {
            UserAuth userAuth = UserAuth.builder()
                    .id(UserUtils.getLoginUser().getId())
                    .password(BCrypt.hashpw(passwordVo.getNewPassword(), BCrypt.gensalt()))
                    .build();
            userAuthDao.updateById(userAuth);
        } else {
            throw new BizException("旧密码不正确");
        }
    }

    @Override
    public void sendCode(String username) {
        // 校验账号是否合法
        if (!checkEmail(username)) {
            throw new BizException("邮箱已被注册！");
        }
        // 生成六位随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("蜗牛客栈注册验证")
                .content("您的验证码为 " + code + " 有效期15分钟，请不要告诉他人哦！")
                .build();
        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE, "*", new Message(JSON.toJSONBytes(emailDTO), new MessageProperties()));
        // 将验证码存入redis，设置过期时间为15分钟
        redisService.set(USER_CODE_KEY + username, code, CODE_EXPIRE_TIME);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void userRegister(UserVO user) {
        // 校验账号是否合法
        if (userIsExisted(user)) {
            throw new BizException("邮箱已被注册！");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(CommonConst.DEFAULT_NICKNAME + IdWorker.getId())
                .avatar(blogInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        userInfoDao.insert(userInfo);
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getId())
                .roleId(USER.getRoleId())
                .build();
        userRoleDao.insert(userRole);
        // 新增用户账号
        String ipAddress = IpUtils.getIpAddress(request);
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getId())
                .username(user.getUsername())
                .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                /*.ipAddress(ipAddress)
                .ipSource(IpUtils.getIpSource(ipAddress))*/
                .build();
        userAuthDao.insert(userAuth);
    }

    @Override
    public UserInfoDTO loginByQQ(QQLoginVO qqLoginVO) {
        return socialLoginStrategyContext.executeLoginStrategy(JSON.toJSONString(qqLoginVO), LoginTypeEnum.QQ);
    }

    @Override
    public void updatePassword(UserVO user) {
        if (!userIsExisted(user)) {
            throw new BizException("邮箱尚未注册！");
        }
        userAuthDao.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getPassword, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
                .eq(UserAuth::getUsername, user.getUsername()));
    }

    private boolean userIsExisted(UserVO user) {
        if (!user.getCode().equals(redisService.get(USER_CODE_KEY+user.getUsername()))) {
            throw new BizException("验证码错误！");
        }
        //查询用户名是否存在
        UserAuth userAuth = userAuthDao.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }


    /**
     * 统计用户地区
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void statisticalUserArea() {
        // 统计用户地域分布
        Map<String, Long> userAreaMap = userAuthDao.selectList(new LambdaQueryWrapper<UserAuth>().select(UserAuth::getIpSource))
                .stream()
                .map(item -> {
                    if (StringUtils.isNotBlank(item.getIpSource())) {
                        return item.getIpSource().substring(0, 2)
                                .replaceAll(PROVINCE, "")
                                .replaceAll(CITY, "");
                    }
                    return UNKNOWN;
                })
                .collect(Collectors.groupingBy(item -> item, Collectors.counting()));
        // 转换格式
        List<UserAreaDTO> userAreaList = userAreaMap.entrySet().stream()
                .map(item -> UserAreaDTO.builder()
                        .name(item.getKey())
                        .value(item.getValue())
                        .build())
                .collect(Collectors.toList());
        redisService.set(USER_AREA, com.alibaba.fastjson.JSON.toJSONString(userAreaList));
    }
}




