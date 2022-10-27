package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dao.FriendLinkDao;
import com.wcy.blog.dto.FriendLinkBackDTO;
import com.wcy.blog.service.FriendLinkService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.FriendLinkVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.wcy.blog.constant.OptTypeConst.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/22:34
 */
@Api(tags = "友链模块")
@RestController
@RequestMapping("/admin/links")
public class LinkAdminController {

    @Autowired
    private FriendLinkService friendLinkService;

    @ApiOperation(value = "查看后台友链列表", notes = "查看后台友链列表")
    @GetMapping
    public Result<PageResult<FriendLinkBackDTO>> listLinksBack(ConditionVO condition) {
        return Result.ok(friendLinkService.listLinksBack(condition));
    }

    @ApiOperation(value = "保存或修改友链", notes = "保存或修改友链")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping
    public Result<?> addOrUpdateLink(@Valid @RequestBody FriendLinkVO friendLinkVO) {
        friendLinkService.addOrUpdateLink(friendLinkVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除友链", notes = "删除友链")
    @OptLog(optType = DELETE)
    @ApiImplicitParam(name = "linkIdList", value = "友链id", required = true, dataType = "List<Integer>")
    @DeleteMapping
    public Result<?> deleteLinks(@RequestBody List<Integer> linkIdList) {
        friendLinkService.deleteLinks(linkIdList);
        return Result.ok();
    }
}
