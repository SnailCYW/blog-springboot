package com.wcy.blog.controller.user;

import com.wcy.blog.dto.BlogHomeInfoDTO;
import com.wcy.blog.service.BlogInfoService;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/19:20
 */
@RestController
@Api(tags = "博客信息模块")
public class BlogInfoUserController {

    @Autowired
    private BlogInfoService blogInfoService;

    @GetMapping
    @ApiOperation(value = "查看博客信息", notes = "查看博客信息")
    public Result<BlogHomeInfoDTO> getBlogInfo() {
        return Result.ok(blogInfoService.getBlogInfo());
    }

    @GetMapping("/about")
    @ApiOperation(value = "查看关于我信息", notes = "查看关于我信息")
    public Result<String> getAboutMe() {
        return Result.ok(blogInfoService.getAboutMe());
    }

}
