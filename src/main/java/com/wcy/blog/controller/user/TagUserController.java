package com.wcy.blog.controller.user;

import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.service.TagService;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/08/17:09
 */
@Api(tags = "标签模块")
@RestController
@RequestMapping("/tags")
public class TagUserController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "查询标签列表", notes = "用户页面查询标签列表")
    @GetMapping()
    public Result<PageResult<TagDTO>> listTags() {
        return Result.ok(tagService.listTags());
    }

}
