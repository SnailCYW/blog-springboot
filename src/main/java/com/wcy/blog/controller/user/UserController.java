package com.wcy.blog.controller.user;

import com.wcy.blog.annotation.AccessLimit;
import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dao.UserAuthDao;
import com.wcy.blog.dto.UserInfoDTO;
import com.wcy.blog.service.UserAuthService;
import com.wcy.blog.vo.QQLoginVO;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationSupport;
import javax.validation.Valid;

import static com.wcy.blog.constant.OptTypeConst.ADD;
import static com.wcy.blog.constant.OptTypeConst.UPDATE;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/22/10:48
 */
@RestController
@Api(tags = "用户账号模块")
public class UserController {

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码", notes = "发送邮箱验证码")
    @ApiImplicitParam(value = "用户名", name = "username", required = true, dataType = "String")
    @GetMapping("/users/code")
    public Result<?> sendCode(String username) {
        userAuthService.sendCode(username);
        return Result.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @OptLog(optType = ADD)
    @PostMapping("/register")
    public Result<Object> userRegister(@Valid @RequestBody UserVO userVO) {
        userAuthService.userRegister(userVO);
        return Result.ok();
    }

    @ApiOperation(value = "qq登录", notes = "qq登录")
    @PostMapping("/users/oauth/qq")
    public Result<UserInfoDTO> loginByQQ(@Valid @RequestBody QQLoginVO qqLoginVO) {
        return Result.ok(userAuthService.loginByQQ(qqLoginVO));
    }

    @ApiOperation(value = "修改密码", notes = "修改密码")
    @OptLog(optType = UPDATE)
    @PutMapping("/users/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        userAuthService.updatePassword(user);
        return Result.ok();
    }
}
