package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.MessageBackDTO;
import com.wcy.blog.service.MessageService;
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
 * @Date: 2022/10/25/10:32
 */
@RestController
@RequestMapping("/admin/messages")
@Api(tags = "留言模块")
public class MessageAdminController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "查看后台留言列表", notes = "查看后台留言列表")
    @GetMapping
    public Result<PageResult<MessageBackDTO>> listMessagesBack(ConditionVO condition) {
        return Result.ok(messageService.listMessagesBack(condition));
    }

    @ApiOperation(value = "删除留言", notes = "删除留言")
    @OptLog(optType = DELETE)
    @ApiImplicitParam(value = "messageIdList", name = "留言id", required = true, dataType = "List<Integer>")
    @DeleteMapping
    public Result<?> deleteMessages(@RequestBody List<Integer> messageIdList) {
        messageService.deleteMessages(messageIdList);
        return Result.ok();
    }

    @ApiOperation(value = "审核留言", notes = "审核留言")
    @PutMapping("/review")
    public Result<?> reviewMessages(@Valid @RequestBody ReviewVO reviewVO) {
        messageService.reviewMessages(reviewVO);
        return Result.ok();
    }

}
