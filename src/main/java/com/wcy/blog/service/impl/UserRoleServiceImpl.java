package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.UserRole;
import com.wcy.blog.service.UserRoleService;
import com.wcy.blog.dao.UserRoleDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_user_role】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole>
    implements UserRoleService{

}




