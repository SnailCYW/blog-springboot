package com.wcy.blog.controller.user;

import com.wcy.blog.dto.*;
import com.wcy.blog.service.ArticleService;
import com.wcy.blog.vo.ConditionVo;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/17:04
 */
@RestController
@RequestMapping("/articles")
@Api(tags = "文章模块")
public class ArticleUserController {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "查看首页文章", notes = "查看首页文章")
    @GetMapping
    public Result<List<ArticleHomeDTO>> listHomeArticles() {
        return Result.ok(articleService.listHomeArticles());
    }

    @ApiOperation(value = "查看文章归档", notes = "查看文章归档")
    @GetMapping("/archives")
    public Result<PageResult<ArchiveDTO>> listArchives() {
        return Result.ok(articleService.listArchives());
    }

    @ApiOperation(value = "根据标签或分类查询文章", notes = "根据标签或分类查询文章")
    @GetMapping("/condition")
    public Result<ArticlePreviewListDTO> listArticleByCondition(ConditionVo condition) {
        return Result.ok(articleService.listArticleByCondition(condition));
    }

    @ApiOperation(value = "根据id查看文章", notes = "根据id查看文章")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "Integer")
    @GetMapping("/{articleId}}")
    public Result<ArticleDTO> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleById(articleId));
    }

    @ApiOperation(value = "搜索文章", notes = "搜索文章")
    @GetMapping("/search")
    public Result<List<ArticleSearchDTO>> searchArticles(ConditionVo condition) {
        return Result.ok(articleService.searchArticles(condition));
    }

}
