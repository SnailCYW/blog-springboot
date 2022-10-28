package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.TalkBackDTO;
import com.wcy.blog.service.TalkService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import com.wcy.blog.vo.TalkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/26/19:32
 */
@RestController
@RequestMapping("/admin/talks")
@Api(tags = "说说模块")
public class TalkAdminController {

    @Autowired
    private TalkService talkService;

    @ApiOperation(value = "查看后台说说", notes = "查看后台说说")
    @GetMapping
    public Result<PageResult<TalkBackDTO>> listTalksBack(ConditionVO condition) {
        return Result.ok(talkService.listTalksBack(condition));
    }

    @ApiOperation(value = "保存或修改说说", notes = "保存或修改说说")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping
    public Result<?> addOrUpdateTalk(@Valid @RequestBody TalkVO talkVO) {
        talkService.addOrUpdateTalk(talkVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除说说", notes = "删除说说")
    @OptLog(optType = DELETE)
    @ApiImplicitParam(value = "talkIdList", name = "说说id", required = true, dataType = "List<Integer>")
    @DeleteMapping
    public Result<?> deleteTalks(@RequestBody List<Integer> talkIdList) {
        talkService.deleteTalks(talkIdList);
        return Result.ok();
    }

    @ApiOperation(value = "上传说说图片", notes = "上传说说图片")
    @ApiImplicitParam(value = "file", name = "说说图片", required = true, dataType = "MultipartFile")
    @PostMapping("/images")
    public Result<String> uploadImages(@RequestBody MultipartFile file) {
        return Result.ok(talkService.uploadTalkImages(file));
    }

    @ApiOperation(value = "根据id查看后台说说", notes = "根据id查看后台说说")
    @GetMapping("/{talkId}")
    public Result<TalkBackDTO> listTalksBackById(@PathVariable("talkId") Integer talkId) {
        return Result.ok(talkService.listTalksBackById(talkId));
    }
}
