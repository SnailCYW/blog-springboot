package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.BlogBackInfoDTO;
import com.wcy.blog.service.BlogInfoService;
import com.wcy.blog.strategy.context.UploadStrategyContext;
import com.wcy.blog.vo.BlogInfoVO;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.WebsiteConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static com.wcy.blog.enums.FilePathEnum.CONFIG;

/**
 * 博客后台信息控制层
 *
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
    @Autowired
    private UploadStrategyContext uploadStrategyContext;

    @ApiOperation(value = "查看后台信息", notes = "查看后台信息")
    @GetMapping
    public Result<BlogBackInfoDTO> getBlogInfoBack() {
        return Result.ok(blogInfoService.getBlogInfoBack());
    }

    @ApiOperation(value = "获取网站配置", notes = "获取网站配置")
    @GetMapping("/website/config")
    public Result<WebsiteConfigVO> getWebsiteConfig() {
        return Result.ok(blogInfoService.getWebsiteConfig());
    }

    @ApiOperation(value = "修改关于我信息", notes = "修改关于我信息")
    @PutMapping("/about")
    public Result<Object> updateAboutMe(@Valid @RequestBody BlogInfoVO blogInfoVO) {
        blogInfoService.updateAboutMe(blogInfoVO);
        return Result.ok();
    }

    @ApiOperation(value = "上传博客配置图片", notes = "上传博客配置图片")
    @ApiImplicitParam(name = "file", value = "图片", required = true, dataType = "MultipartFile")
    @PostMapping("/config/images")
    public Result<String> uploadConfigImages(MultipartFile file) {
        return Result.ok(uploadStrategyContext.executeUploadStrategy(file, CONFIG.getPath()));
    }

    @ApiOperation(value = "更新网站配置", notes = "更新网站配置")
    @PutMapping("/website/config")
    public Result<Object> updateWebsiteConfig(@Valid @RequestBody WebsiteConfigVO websiteConfigVO) {
        blogInfoService.updateWebsiteConfig(websiteConfigVO);
        return Result.ok();
    }

}
