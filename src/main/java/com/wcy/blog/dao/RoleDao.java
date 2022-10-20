package com.wcy.blog.dao;

import com.wcy.blog.dto.ResourceRoleDTO;
import com.wcy.blog.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_role】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.Role
*/
public interface RoleDao extends BaseMapper<Role> {

    /**
     * 查询路由角色列表
     *
     * @return 角色标签
     */
    List<ResourceRoleDTO> listResourceRoles();

    List<String> listRolesByUserInfoId(@Param("userInfoId") Integer userInfoId);
}




