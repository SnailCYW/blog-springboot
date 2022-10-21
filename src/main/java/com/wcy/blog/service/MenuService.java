package com.wcy.blog.service;

import com.wcy.blog.dto.LabelOptionDTO;
import com.wcy.blog.dto.MenuDTO;
import com.wcy.blog.dto.UserMenuDTO;
import com.wcy.blog.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.MenuVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_menu】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface MenuService extends IService<Menu> {

    List<MenuDTO> listMenus(ConditionVO condition);

    void addOrUpdateMenus(MenuVO menuVO);

    void deleteMenuById(Integer menuId);

    List<LabelOptionDTO> listRoleMenus(ConditionVO condition);

    List<UserMenuDTO> listUserMenus();
}
