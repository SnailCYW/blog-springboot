package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.constant.CommonConst;
import com.wcy.blog.dao.RoleMenuDao;
import com.wcy.blog.dao.RoleResourceDao;
import com.wcy.blog.dto.ResourceDTO;
import com.wcy.blog.dto.RoleDTO;
import com.wcy.blog.dto.UserRoleDTO;
import com.wcy.blog.entity.Role;
import com.wcy.blog.entity.RoleMenu;
import com.wcy.blog.entity.RoleResource;
import com.wcy.blog.exception.BizException;
import com.wcy.blog.handler.FilterInvocationSecurityMetadataSourceImpl;
import com.wcy.blog.service.RoleMenuService;
import com.wcy.blog.service.RoleResourceService;
import com.wcy.blog.service.RoleService;
import com.wcy.blog.dao.RoleDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Snail
 * @description 针对表【tb_role】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {


    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleResourceDao roleResourceDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private RoleResourceService roleResourceService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;

    @Override
    public void deleteRoles(List<Integer> roleIdList) {
        roleDao.deleteBatchIds(roleIdList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateRole(RoleVO roleVO) {
        // 判断角色名重复
        Role existRole = roleDao.selectOne(new LambdaQueryWrapper<Role>()
                .select(Role::getId)
                .eq(Role::getRoleName, roleVO.getRoleName()));
        if (Objects.nonNull(existRole) && !existRole.getId().equals(roleVO.getId())) {
            throw new BizException("角色名已存在");
        }
        // 保存或更新角色信息
        Role role = Role.builder()
                .id(roleVO.getId())
                .roleName(roleVO.getRoleName())
                .roleLabel(roleVO.getRoleLabel())
                .isDisable(CommonConst.FALSE)
                .build();
        this.saveOrUpdate(role);
        if (CollectionUtils.isNotEmpty(roleVO.getMenuIdList())) {
            List<RoleMenu> existRoleMenus = roleMenuDao.selectList(new LambdaQueryWrapper<RoleMenu>()
                    .eq(RoleMenu::getRoleId, role.getId()));

            // 删除原先存在的菜单绑定
            roleMenuDao.delete(new LambdaQueryWrapper<RoleMenu>()
                    .eq(RoleMenu::getRoleId, role.getId()));
            // 添加菜单绑定
            List<RoleMenu> roleMenuList = roleVO.getMenuIdList().stream()
                    .map(item -> RoleMenu.builder()
                            .roleId(role.getId())
                            .menuId(item)
                            .build())
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(roleMenuList);
        }
        if (CollectionUtils.isNotEmpty(roleVO.getResourceIdList())) {
            // 删除原先存在的资源绑定
            roleResourceDao.delete(new LambdaQueryWrapper<RoleResource>()
                    .eq(RoleResource::getRoleId, role.getId()));
            // 添加资源绑定
            List<RoleResource> roleResourceList = roleVO.getResourceIdList().stream()
                    .map(item -> RoleResource.builder()
                            .roleId(role.getId())
                            .resourceId(item)
                            .build())
                    .collect(Collectors.toList());
            roleResourceService.saveBatch(roleResourceList);
            // 重新加载角色资源信息
            filterInvocationSecurityMetadataSource.clearDataSource();
        }

    }

    @Override
    public PageResult<RoleDTO> listRolesBack(ConditionVO condition) {
        List<Role> roleList = roleDao.selectList(new LambdaQueryWrapper<Role>()
                .like(Objects.nonNull(condition.getKeywords()), Role::getRoleName, condition.getKeywords()));
        List<RoleDTO> roleDTOList = roleList.stream().map(item -> {
            RoleDTO roleDTO = BeanCopyUtils.copyObject(item, RoleDTO.class);
            List<Integer> menuIdList = roleMenuDao.selectList(new LambdaQueryWrapper<RoleMenu>()
                    .eq(RoleMenu::getRoleId, item.getId()))
                    .stream()
                    .map(RoleMenu::getMenuId)
                    .collect(Collectors.toList());
            List<Integer> resourceIdList = roleResourceDao.selectList(new LambdaQueryWrapper<RoleResource>()
                    .eq(RoleResource::getRoleId, item.getId()))
                    .stream()
                    .map(RoleResource::getResourceId)
                    .collect(Collectors.toList());
            roleDTO.setMenuIdList(menuIdList);
            roleDTO.setResourceIdList(resourceIdList);
            return roleDTO;
        }).collect(Collectors.toList());
        return new PageResult<>(roleList.size(), roleDTOList);
    }

    @Override
    public List<UserRoleDTO> listUserRolesBack() {
        List<Role> roleList = roleDao.selectList(null);
        List<UserRoleDTO> userRoleDTOList = BeanCopyUtils.copyList(roleList, UserRoleDTO.class);
        return userRoleDTOList;
    }
}




