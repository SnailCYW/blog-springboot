package com.wcy.blog.controller;

import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.entity.Tag;
import com.wcy.blog.service.TagService;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    public Result<PageResult<TagDTO>> TagList() {
        return Result.ok(tagService.TagList());
    }

    @ApiOperation(value = "后台查询标签列表", notes = "后台页面查询标签列表")
    @GetMapping("/admin/tags")
    public Result<PageResult<TagBackDTO>> TagListBack(ConditionVo conditionVo) {
        return Result.ok(tagService.TagListBack(conditionVo));
    }

}
