package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.RoleDTO;
import com.wcy.blog.dto.UserRoleDTO;
import com.wcy.blog.service.RoleService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/27/12:09
 */
@Api(tags = "角色模块")
@RestController
@RequestMapping("/admin")
public class RoleAdminController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @OptLog(optType = DELETE)
    @DeleteMapping("/roles")
    public Result<?> deleteRoles(@Valid @RequestBody List<Integer> roleIdList) {
        roleService.deleteRoles(roleIdList);
        return Result.ok();
    }

    @ApiOperation(value = "保存或更新角色", notes = "保存或更新角色")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping("/role")
    public Result<?> addOrUpdateRole(@Valid @RequestBody RoleVO roleVO) {
        roleService.addOrUpdateRole(roleVO);
        return Result.ok();
    }

    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @GetMapping("/roles")
    public Result<PageResult<RoleDTO>> listRolesBack(ConditionVO condition) {
        return Result.ok(roleService.listRolesBack(condition));
    }

    @ApiOperation(value = "查询用户角色选项", notes = "查询用户角色选项")
    @GetMapping("/users/role")
    public Result<List<UserRoleDTO>> listUserRolesBack() {
        return Result.ok(roleService.listUserRolesBack());
    }

}
