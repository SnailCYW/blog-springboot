package com.wcy.blog.controller.user;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.CommentDTO;
import com.wcy.blog.dto.ReplyDTO;
import com.wcy.blog.service.CommentService;
import com.wcy.blog.vo.CommentVO;
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
 * @Date: 2022/10/25/15:04
 */
@RestController
@RequestMapping("/comments")
@Api(tags = "评论模块")
public class CommentUserController {

    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "查询评论", notes = "查询评论")
    @GetMapping
    public Result<PageResult<CommentDTO>> listComment(CommentVO commentVO) {
        return Result.ok(commentService.listComment(commentVO));
    }

    @ApiOperation(value = "添加评论", notes = "添加评论")
    @OptLog(optType = ADD)
    @PostMapping
    public Result<?> addComment(@Valid @RequestBody CommentVO commentVO) {
        commentService.addComment(commentVO);
        return Result.ok();
    }

    @ApiOperation(value = "评论点赞", notes = "评论点赞")
    @PostMapping("/{commentId}/like")
    public Result<?> likeComment(@PathVariable("commentId") Integer commentId) {
        commentService.likeComment(commentId);
        return Result.ok();
    }

    @ApiOperation(value = "查询评论", notes = "查询评论")
    @GetMapping("/{commentId}/replies")
    public Result<List<ReplyDTO>> listCommentByParentId(@PathVariable("commentId") Integer commentId) {
        return Result.ok(commentService.listCommentByParentId(commentId));
    }

}
