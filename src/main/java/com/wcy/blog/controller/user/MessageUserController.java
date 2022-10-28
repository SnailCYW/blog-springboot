package com.wcy.blog.controller.user;

import com.wcy.blog.annotation.AccessLimit;
import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.MessageDTO;
import com.wcy.blog.service.MessageService;
import com.wcy.blog.vo.MessageVO;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.ADD;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/25/10:51
 */
@RestController
@RequestMapping("/messages")
@Api(tags = "留言模块")
public class MessageUserController {

    @Autowired
    private MessageService messageService;

    @ApiOperation(value = "查看留言列表", notes = "查看留言列表")
    @GetMapping
    public Result<List<MessageDTO>> listMessages() {
        return Result.ok(messageService.listMessages());
    }

    @ApiOperation(value = "添加留言", notes = "添加留言")
    @AccessLimit(seconds = 60, maxCount = 1)
    @OptLog(optType = ADD)
    @PostMapping
    public Result<List<MessageDTO>> addMessage(@Valid @RequestBody MessageVO messageVO) {
        messageService.addMessage(messageVO);
        return Result.ok();
    }

}
