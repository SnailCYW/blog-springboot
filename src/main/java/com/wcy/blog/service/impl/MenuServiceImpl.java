package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.Menu;
import com.wcy.blog.service.MenuService;
import com.wcy.blog.dao.MenuDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_menu】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu>
    implements MenuService{

}




