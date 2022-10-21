package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dao.RoleMenuDao;
import com.wcy.blog.dto.LabelOptionDTO;
import com.wcy.blog.dto.MenuDTO;
import com.wcy.blog.dto.UserDetailDTO;
import com.wcy.blog.dto.UserMenuDTO;
import com.wcy.blog.entity.Menu;
import com.wcy.blog.service.MenuService;
import com.wcy.blog.dao.MenuDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.UserUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.wcy.blog.constant.CommonConst.COMPONENT;
import static com.wcy.blog.constant.CommonConst.TRUE;

/**
* @author Snail
* @description 针对表【tb_menu】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu>
    implements MenuService{

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;

    @Override
    public List<MenuDTO> listMenus(ConditionVO condition) {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Menu::getName, condition.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);

        // 组装目录菜单数据
        List<MenuDTO> menuDTOList = catalogList.stream().map(item -> {
            MenuDTO menuDTO = BeanCopyUtils.copyObject(item, MenuDTO.class);
            List<MenuDTO> childrenList = BeanCopyUtils.copyList(childrenMap.get(item.getId()), MenuDTO.class).stream()
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTO.setChildren(childrenList);
            childrenMap.remove(item.getId());
            return menuDTO;
        }).sorted(Comparator.comparing(MenuDTO::getOrderNum)).collect(Collectors.toList());

        // 若还有菜单未取出则拼接到目录
        if (CollectionUtils.isNotEmpty(childrenMap)) {
            List<Menu> childrenList = new ArrayList<>();
            childrenMap.values().forEach(childrenList::addAll);
            List<MenuDTO> childrenDTOList = childrenList.stream()
                    .map(item -> BeanCopyUtils.copyObject(item, MenuDTO.class))
                    .sorted(Comparator.comparing(MenuDTO::getOrderNum))
                    .collect(Collectors.toList());
            menuDTOList.addAll(childrenDTOList);
        }
        return menuDTOList;
    }

    @Override
    public void addOrUpdateMenus(MenuVO menuVO) {
        Menu menu = BeanCopyUtils.copyObject(menuVO, Menu.class);
        saveOrUpdate(menu);
    }

    @Override
    public void deleteMenuById(Integer menuId) {
        menuDao.deleteById(menuId);
    }

    @Override
    public List<LabelOptionDTO> listRoleMenus(ConditionVO condition) {
        // 查询菜单数据
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>()
                .select(Menu::getId, Menu::getName, Menu::getParentId, Menu::getOrderNum)
                .like(StringUtils.isNotBlank(condition.getKeywords()), Menu::getName, condition.getKeywords()));
        // 获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        // 获取目录下的子菜单
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);

        // 组装目录菜单数据
        return catalogList.stream().map(item -> {
            List<LabelOptionDTO> children = new ArrayList<>();
            List<Menu> childrenMenus = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(childrenMenus)) {
                children = childrenMenus.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> LabelOptionDTO.builder()
                                .id(menu.getId())
                                .label(menu.getName())
                                .build())
                        .collect(Collectors.toList());
            }
            return LabelOptionDTO.builder()
                    .id(item.getId())
                    .label(item.getName())
                    .children(children)
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public List<UserMenuDTO> listUserMenus() {
        List<Menu> menuList = menuDao.listUserMenus(UserUtils.getLoginUser().getUserInfoId());
        List<Menu> catalogList = listCatalog(menuList);
        Map<Integer, List<Menu>> childrenMap = getMenuMap(menuList);
        return convertUserMenuDTOList(catalogList, childrenMap);
    }

    private List<UserMenuDTO> convertUserMenuDTOList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(item -> {
            // 获取目录
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            // 获取目录下的子菜单
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                // 多级菜单处理
                userMenuDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrderNum))
                        .map(menu -> {
                            UserMenuDTO dto = BeanCopyUtils.copyObject(menu, UserMenuDTO.class);
                            dto.setHidden(menu.getIsHidden().equals(TRUE));
                            return dto;
                        })
                        .collect(Collectors.toList());
            } else {
                // 一级菜单处理
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent(COMPONENT);
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getIsHidden().equals(TRUE));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 获取目录下菜单列表
     *
     * @param menuList 菜单列表
     * @return 目录下的菜单列表
     */
    private Map<Integer, List<Menu>> getMenuMap(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParentId()))
                .collect(Collectors.groupingBy(Menu::getParentId));
    }

    /**
     * 获取目录列表
     *
     * @param menuList 菜单列表
     * @return 目录列表
     */
    private List<Menu> listCatalog(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.isNull(item.getParentId()))
                .sorted(Comparator.comparing(Menu::getOrderNum))
                .collect(Collectors.toList());
    }
}




