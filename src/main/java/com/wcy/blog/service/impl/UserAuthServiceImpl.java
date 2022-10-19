package com.wcy.blog.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.UserAreaDTO;
import com.wcy.blog.dto.UserBackDTO;
import com.wcy.blog.entity.UserAuth;
import com.wcy.blog.service.RedisService;
import com.wcy.blog.service.UserAuthService;
import com.wcy.blog.dao.UserAuthDao;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.RedisPrefixConst.USER_AREA;
import static com.wcy.blog.constant.RedisPrefixConst.VISITOR_AREA;
import static com.wcy.blog.enums.UserAreaTypeEnum.getUserAreaType;

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
    private RedisService redisService;

    @Override
    public PageResult<UserBackDTO> listUsersBack(ConditionVo condition) {
        Integer count = userAuthDao.selectCount(null);
        if (count == 0) {
            return new PageResult<>();
        }
        List<UserBackDTO> userBackDTOList = userAuthDao.listUsersBack(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResult<>(count, userBackDTOList);
    }

    @Override
    public List<UserAreaDTO> getUsersArea(ConditionVo condition) {
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
                                    .value((Integer) item.getValue())
                                    .build())
                            .collect(Collectors.toList());
                }
                return userAreaDTOList;
        }
        return userAreaDTOList;
    }
}




