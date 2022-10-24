package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.service.TagService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.TagVO;
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
 * @Date: 2022/10/08/17:09
 */
@Api(tags = "标签模块")
@RestController
@RequestMapping("/admin/tags")
public class TagAdminController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "后台查询标签列表", notes = "后台页面查询标签列表")
    @GetMapping()
    public Result<PageResult<TagBackDTO>> listTagsBack(ConditionVO conditionVo) {
        return Result.ok(tagService.listTagsBack(conditionVo));
    }

    @ApiOperation(value = "搜索文章标签", notes = "搜索文章标签")
    @GetMapping("/search")
    public Result<List<TagDTO>> searchTag(ConditionVO conditionVo) {
        return Result.ok(tagService.searchTag(conditionVo));
    }

    @ApiOperation(value = "添加或修改标签", notes = "添加或修改标签")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping()
    public Result<Object> addOrModifyTags(@Valid @RequestBody TagVO tagVO) {
        tagService.addOrModifyTags(tagVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除标签", notes = "删除标签")
    @OptLog(optType = DELETE)
    @DeleteMapping()
    public Result<Object> deleteTags(@Valid @RequestBody List<Integer> tagIdList) {
        tagService.deleteTags(tagIdList);
        return Result.ok();
    }

}
