package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.CategoryBackDTO;
import com.wcy.blog.dto.CategoryDTO;
import com.wcy.blog.dto.CategoryOptionDTO;
import com.wcy.blog.service.CategoryService;
import com.wcy.blog.vo.CategoryVO;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/17:41
 */
@Api(tags = "分类模块")
@RestController
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "查看后台分类列表", notes = "查看后台分类列表")
    @GetMapping("/admin/categories")
    public Result<PageResult<CategoryBackDTO>> listCategoriesBack(ConditionVo condition) {
        return Result.ok(categoryService.listCategoriesBack(condition));
    }

    @ApiOperation(value = "搜索文章分类", notes = "搜索文章分类")
    @GetMapping("/admin/categories/search")
    public Result<List<CategoryOptionDTO>> searchCategories(ConditionVo condition) {
        return Result.ok(categoryService.searchCategories(condition));
    }

    @ApiOperation(value = "添加或修改分类", notes = "添加或修改分类")
    @PostMapping("/admin/categories")
    public Result<Object> addOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.addOrUpdateCategory(categoryVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除分类", notes = "删除分类")
    @DeleteMapping("/admin/categories")
    public Result<Object> deleteCategories(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategories(categoryIdList);
        return Result.ok();
    }
}
