package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.ArticleBackDTO;
import com.wcy.blog.service.ArticleService;
import com.wcy.blog.vo.ArticleVo;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result<ArticleVo> getArticleById(@PathVariable("articleId") Integer articleId) {
        return Result.ok(articleService.getArticleBackById(articleId));
    }

    /*@ApiOperation(value = "添加或修改文章", notes = "添加或修改文章")
    @PostMapping()
    public Result<Object> addOrUpdateArticle(@Valid @RequestBody ArticleVo articleVo) {
        articleService.addOrUpdateArticle(articleVo);
        return Result.ok();
    }*/

}
