package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.ArticleBackDTO;
import com.wcy.blog.service.ArticleService;
import com.wcy.blog.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/17/10:38
 */

@Api(tags = "文章模块")
@RestController
@RequestMapping("/admin/articles")
public class ArticleAdminController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "查看后台文章", notes = "查看后台文章")
    @GetMapping()
    public Result<PageResult<ArticleBackDTO>> listArticleBack(ConditionVO condition) {
        return Result.ok(articleService.listArticleBack(condition));
    }

    @ApiOperation(value = "根据id查看后台文章", notes = "根据id查看后台文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/{articleId}")
    public Result<ArticleVO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleBackById(articleId));
    }

    @ApiOperation(value = "添加或修改文章", notes = "添加或修改文章")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping()
    public Result<Object> addOrUpdateArticle(@Valid @RequestBody ArticleVO articleVo) {
        articleService.addOrUpdateArticle(articleVo);
        return Result.ok();
    }

    @ApiOperation(value = "恢复或删除文章", notes = "恢复或删除文章")
    @OptLog(optType = UPDATE)
    @PutMapping()
    public Result<?> deleteOrRestoreArticle(@Valid @RequestBody DeleteVO deleteVO) {
        articleService.deleteOrRestoreArticle(deleteVO);
        return Result.ok();
    }

    @ApiOperation(value = "物理删除文章", notes = "物理删除文章")
    @OptLog(optType = DELETE)
    @ApiImplicitParam(value = "articleIdList", name = "文章id", required = true, dataType = "List<Integer>")
    @DeleteMapping()
    public Result<?> deleteArticle(@Valid @RequestBody List<Integer> articleIdList) {
        articleService.deleteArticle(articleIdList);
        return Result.ok();
    }

    /**
     * 导出文章
     *
     * @param articleIdList 文章id列表
     * @return {@link List<String>} 文件url列表
     */
    @ApiOperation(value = "导出文章")
    @ApiImplicitParam(name = "articleIdList", value = "文章id", required = true, dataType = "List<Integer>")
    @PostMapping("/export")
    public Result<List<String>> exportArticles(@RequestBody List<Integer> articleIdList) {
        return Result.ok(articleService.exportArticles(articleIdList));
    }

    @ApiOperation(value = "上传文章图片")
    @ApiImplicitParam(name = "file", value = "文章图片", required = true, dataType = "MultipartFile")
    @PostMapping("/images")
    public Result<String> uploadImages(@RequestBody MultipartFile file) {
        return Result.ok(articleService.uploadImages(file));
    }

    @ApiOperation(value = "导入文章")
    @PostMapping("/import")
    public Result<?> importArticles(MultipartFile file, String type) {
        articleService.importArticles(file, type);
        return Result.ok();
    }

    @ApiOperation(value = "修改文章置顶")
    @OptLog(optType = UPDATE)
    @PostMapping("/top")
    public Result<?> updateArticleWithTop(@Valid @RequestBody ArticleTopVO articleTopVO) {
        articleService.updateArticleWithTop(articleTopVO);
        return Result.ok();
    }

}
