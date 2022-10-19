package com.wcy.blog.service;

import com.wcy.blog.dto.UserAreaDTO;
import com.wcy.blog.dto.UserBackDTO;
import com.wcy.blog.entity.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_user_auth】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface UserAuthService extends IService<UserAuth> {

    PageResult<UserBackDTO> listUsersBack(ConditionVo condition);

    List<UserAreaDTO> getUsersArea(ConditionVo condition);
}
