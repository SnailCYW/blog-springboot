package com.wcy.blog.controller.user;

import com.wcy.blog.dto.CategoryDTO;
import com.wcy.blog.service.CategoryService;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/17:41
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping("/categories")
public class CategoryUserController {

    @Autowired
    private CategoryService categoryService;


    @ApiOperation(value = "查看分类列表", notes = "查看分类列表")
    @GetMapping()
    public Result<PageResult<CategoryDTO>> listCategories() {
        return Result.ok(categoryService.listCategories());
    }

}
