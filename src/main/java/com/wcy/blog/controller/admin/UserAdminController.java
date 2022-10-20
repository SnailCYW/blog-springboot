package com.wcy.blog.controller.admin;

import com.wcy.blog.dao.UserAuthDao;
import com.wcy.blog.dto.UserAreaDTO;
import com.wcy.blog.dto.UserBackDTO;
import com.wcy.blog.entity.UserAuth;
import com.wcy.blog.service.UserAuthService;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.PasswordVo;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/20:33
 */
@RestController
@RequestMapping("/admin/users")
@Api(tags = "用户账号模块")
public class UserAdminController {

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "查询后台用户列表", notes = "查询后台用户列表")
    @GetMapping()
    public Result<PageResult<UserBackDTO>> listUsersBack(ConditionVo condition) {
        return Result.ok(userAuthService.listUsersBack(condition));
    }

    @ApiOperation(value = "获取用户区域分布", notes = "获取用户区域分布")
    @GetMapping("/area")
    public Result<List<UserAreaDTO>> getUsersArea(ConditionVo condition) {
        return Result.ok(userAuthService.getUsersArea(condition));
    }

    @ApiOperation(value = "修改管理员密码", notes = "修改管理员密码")
    @PutMapping("/password")
    public Result<?> updateAdminPassword(@Valid @RequestBody PasswordVo passwordVo) {
        userAuthService.updateAdminPassword(passwordVo);
        return Result.ok();
    }
}
