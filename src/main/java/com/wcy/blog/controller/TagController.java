package com.wcy.blog.controller;

import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.entity.Page;
import com.wcy.blog.service.TagService;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/08/17:09
 */
@Api(tags = "标签模块")
@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "查询标签列表", notes = "用户页面查询标签列表")
    @GetMapping("/tags")
    public Result<PageResult<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }

    @ApiOperation(value = "后台查询标签列表", notes = "后台页面查询标签列表")
    @GetMapping("/admin/tags")
    public Result<PageResult<TagBackDTO>> listTagsBack(ConditionVo conditionVo) {
        return Result.ok(tagService.listTagsBack(conditionVo));
    }

    @ApiOperation(value = "搜索文章标签", notes = "搜索文章标签")
    @GetMapping("/admin/tags/search")
    public Result<List<TagDTO>> searchTag(ConditionVo conditionVo) {
        return Result.ok(tagService.searchTag(conditionVo));
    }

}
