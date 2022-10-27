package com.wcy.blog.controller.user;

import com.wcy.blog.service.UserInfoService;
import com.wcy.blog.vo.EmailVO;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.UserDisableVO;
import com.wcy.blog.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/27/9:29
 */
@Api(tags = "用户信息模块")
@RestController
@RequestMapping("/users")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "更新用户头像", notes = "更新用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.ok(userInfoService.updateUserAvatar(file));
    }

    @ApiOperation(value = "绑定用户邮箱", notes = "绑定用户邮箱")
    @PostMapping("/email")
    public Result<?> bindUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.bindUserEmail(emailVO);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @PutMapping("/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.ok();
    }
}
