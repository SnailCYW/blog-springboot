package com.wcy.blog.dao;

import com.wcy.blog.dto.UserMenuDTO;
import com.wcy.blog.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_menu】的数据库操作Mapper
* @createDate 2022-09-25 22:58:52
* @Entity com.wcy.blog.entity.Menu
*/
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> listUserMenus(@Param("userInfoId") Integer userId);
}




