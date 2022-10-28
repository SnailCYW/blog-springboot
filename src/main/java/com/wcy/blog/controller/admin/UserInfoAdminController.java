package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.UserOnlineDTO;
import com.wcy.blog.service.UserInfoService;
import com.wcy.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.wcy.blog.constant.OptTypeConst.ADD;
import static com.wcy.blog.constant.OptTypeConst.UPDATE;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/27/8:22
 */
@Api(tags = "用户信息模块")
@RestController
@RequestMapping("/admin/users")
public class UserInfoAdminController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "修改用户禁用状态", notes = "修改用户禁用状态")
    @OptLog(optType = UPDATE)
    @PutMapping("/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.ok();
    }

    @ApiOperation(value = "查看在线用户", notes = "查看在线用户")
    @GetMapping("/online")
    public Result<PageResult<UserOnlineDTO>> listOnlineUsers(ConditionVO condition) {
        return Result.ok(userInfoService.listOnlineUsers(condition));
    }

    @ApiOperation(value = "修改用户角色", notes = "修改用户角色")
    @OptLog(optType = UPDATE)
    @PutMapping("/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.ok();
    }

    @ApiOperation(value = "下线用户", notes = "下线用户")
    @DeleteMapping("/{userInfoId}/online")
    public Result<?> offlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.offlineUser(userInfoId);
        return Result.ok();
    }

}
