package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.service.PageService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.PageVO;
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
 * @Date: 2022/10/27/10:55
 */
@Api(tags = "页面模块")
@RestController
@RequestMapping("/admin/pages")
public class PageAdminController {

    @Autowired
    private PageService pageService;

    @ApiOperation(value = "获取页面列表", notes = "获取页面列表")
    @GetMapping()
    public Result<List<PageVO>> listPages() {
        return Result.ok(pageService.listPages());
    }

    @ApiOperation(value = "保存或更新页面", notes = "保存或更新页面")
    @PostMapping()
    public Result<?> addOrUpdatePage(@Valid @RequestBody PageVO pageVO) {
        pageService.addOrUpdatePage(pageVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除页面", notes = "删除页面")
    @ApiImplicitParam(name = "pageId", value = "页面id", required = true, dataType = "Integer")
    @DeleteMapping("/{pageId}")
    public Result<?> deletePage(@PathVariable("pageId") Integer pageId) {
        pageService.deletePage(pageId);
        return Result.ok();
    }
}
