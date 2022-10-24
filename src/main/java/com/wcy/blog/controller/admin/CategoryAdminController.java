package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.CategoryBackDTO;
import com.wcy.blog.dto.CategoryOptionDTO;
import com.wcy.blog.service.CategoryService;
import com.wcy.blog.vo.CategoryVO;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/17:41
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "查看后台分类列表", notes = "查看后台分类列表")
    @GetMapping()
    public Result<PageResult<CategoryBackDTO>> listCategoriesBack(ConditionVO condition) {
        return Result.ok(categoryService.listCategoriesBack(condition));
    }

    @ApiOperation(value = "搜索文章分类", notes = "搜索文章分类")
    @GetMapping("/search")
    public Result<List<CategoryOptionDTO>> searchCategories(ConditionVO condition) {
        return Result.ok(categoryService.searchCategories(condition));
    }

    @ApiOperation(value = "添加或修改分类", notes = "添加或修改分类")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping()
    public Result<Object> addOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.addOrUpdateCategory(categoryVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除分类", notes = "删除分类")
    @OptLog(optType = DELETE)
    @DeleteMapping()
    public Result<Object> deleteCategories(@RequestBody List<Integer> categoryIdList) {
        categoryService.deleteCategories(categoryIdList);
        return Result.ok();
    }
}
