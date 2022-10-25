package com.wcy.blog.controller.user;

import com.wcy.blog.dto.FriendLinkBackDTO;
import com.wcy.blog.dto.FriendLinkDTO;
import com.wcy.blog.service.FriendLinkService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/25/9:39
 */
@Api(tags = "分类模块")
@RestController
@RequestMapping("/links")
public class LinkUserController {

    @Autowired
    private FriendLinkService friendLinkService;

    @ApiOperation(value = "查看友链列表", notes = "查看友链列表")
    @GetMapping
    public Result<PageResult<FriendLinkDTO>> listLinks() {
        return Result.ok(friendLinkService.listLinks());
    }

}
