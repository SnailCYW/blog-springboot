package com.wcy.blog.service;

import com.wcy.blog.dto.RoleDTO;
import com.wcy.blog.dto.UserRoleDTO;
import com.wcy.blog.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.RoleVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_role】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface RoleService extends IService<Role> {

    void deleteRoles(List<Integer> roleIdList);

    void addOrUpdateRole(RoleVO roleVO);

    PageResult<RoleDTO> listRolesBack(ConditionVO condition);

    List<UserRoleDTO> listUserRolesBack();
}
