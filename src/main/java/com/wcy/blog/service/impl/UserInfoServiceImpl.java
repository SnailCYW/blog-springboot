package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.UserInfo;
import com.wcy.blog.service.UserInfoService;
import com.wcy.blog.dao.UserInfoDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_user_info】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo>
    implements UserInfoService{

}




