package com.wcy.blog.controller.user;

import com.wcy.blog.dto.TalkBackDTO;
import com.wcy.blog.dto.TalkDTO;
import com.wcy.blog.service.TalkService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/26/20:36
 */
@RestController
@Api(tags = "说说模块")
public class TalkUserController {

    @Autowired
    private TalkService talkService;

    @ApiOperation(value = "查看首页说说", notes = "查看首页说说")
    @GetMapping("/home/talks")
    public Result<List<String>> listHomeTalks() {
        return Result.ok(talkService.listHomeTalks());
    }

    @ApiOperation(value = "查看说说列表", notes = "查看说说列表")
    @GetMapping("/talks")
    public Result<PageResult<TalkDTO>> listTalks() {
        return Result.ok(talkService.listTalks());
    }

    @ApiOperation(value = "根据id查看说说", notes = "根据id查看说说")
    @ApiImplicitParam(value = "talkId", name = "说说id", required = true, dataType = "Integer")
    @GetMapping("/talks/{talkId}")
    public Result<TalkDTO> getTalkById(@PathVariable("talkId") Integer talkId) {
        return Result.ok(talkService.getTalkById(talkId));
    }

    @ApiOperation(value = "点赞说说", notes = "点赞说说")
    @ApiImplicitParam(value = "talkId", name = "说说id", required = true, dataType = "Integer")
    @PostMapping("/talks/{talkId}/like")
    public Result<?> likeTalk(@PathVariable("talkId") Integer talkId) {
        talkService.likeTalk(talkId);
        return Result.ok();
    }
}
