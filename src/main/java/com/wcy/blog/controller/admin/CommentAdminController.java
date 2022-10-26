package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.CommentBackDTO;
import com.wcy.blog.service.CommentService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.ReviewVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.ADD;
import static com.wcy.blog.constant.OptTypeConst.DELETE;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/25/11:55
 */
@RestController
@RequestMapping("/admin/comments")
@Api(tags = "评论模块")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "查询后台评论", notes = "查询后台评论")
    @GetMapping
    public Result<PageResult<CommentBackDTO>> listCommentBack(ConditionVO condition) {
        return Result.ok(commentService.listCommentBack(condition));
    }

    @ApiOperation(value = "删除评论", notes = "删除评论")
    @OptLog(optType = DELETE)
    @ApiImplicitParam(value = "commentIdList", name = "评论id", required = true, dataType = "List<Integer>")
    @DeleteMapping
    public Result<?> deleteComment(@RequestBody List<Integer> commentIdList) {
        commentService.deleteComment(commentIdList);
        return Result.ok();
    }

    @ApiOperation(value = "审核评论", notes = "审核评论")
    @PutMapping("/review")
    public Result<?> reviewComment(@Valid @RequestBody ReviewVO reviewVO) {
        commentService.reviewComment(reviewVO);
        return Result.ok();
    }
}
