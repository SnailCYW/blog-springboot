package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.LabelOptionDTO;
import com.wcy.blog.dto.MenuDTO;
import com.wcy.blog.dto.UserMenuDTO;
import com.wcy.blog.service.MenuService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.MenuVO;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/21/11:48
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "菜单模块")
public class MenusAdminController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查看菜单列表", notes = "查看菜单列表")
    @GetMapping("/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVO condition) {
        return Result.ok(menuService.listMenus(condition));
    }

    @ApiOperation(value = "新增或修改菜单", notes = "新增或修改菜单")
    @PostMapping("/menus")
    public Result<?> addOrUpdateMenus(@Valid @RequestBody MenuVO menuVO) {
        menuService.addOrUpdateMenus(menuVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    @ApiImplicitParam(name = "menuId",value = "menuId",required = true, dataType = "Integer")
    @DeleteMapping("/menus/{menuId}")
    public Result<?> deleteMenuById(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenuById(menuId);
        return Result.ok();
    }

    @ApiOperation(value = "查看角色菜单选项", notes = "查看角色菜单选项")
    @GetMapping("/role/menus")
    public Result<List<LabelOptionDTO>> listRoleMenus(ConditionVO condition) {
        return Result.ok(menuService.listRoleMenus(condition));
    }

    @ApiOperation(value = "查看当前用户菜单", notes = "查看当前用户菜单")
    @GetMapping("/user/menus")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.ok(menuService.listUserMenus());
    }

}
