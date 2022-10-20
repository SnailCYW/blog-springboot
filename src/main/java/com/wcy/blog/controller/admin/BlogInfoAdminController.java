package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.BlogBackInfoDTO;
import com.wcy.blog.service.BlogInfoService;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/21:14
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "博客信息模块")
public class BlogInfoAdminController {

    @Autowired
    private BlogInfoService blogInfoService;

    @ApiOperation(value = "查看后台信息", notes = "查看后台信息")
    @GetMapping
    public Result<BlogBackInfoDTO> getBlogInfoBack() {
        return Result.ok(blogInfoService.getBlogInfoBack());
    }

}
