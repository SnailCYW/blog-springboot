package com.wcy.blog.controller.admin;

import com.wcy.blog.annotation.OptLog;
import com.wcy.blog.dto.LabelOptionDTO;
import com.wcy.blog.dto.ResourceDTO;
import com.wcy.blog.service.ResourceService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.ResourceVO;
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
 * @Date: 2022/10/27/12:13
 */
@Api(tags = "页面模块")
@RestController
@RequestMapping("/admin")
public class ResourceAdminController {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "查看资源列表", notes = "查看资源列表")
    @GetMapping("/resources")
    public Result<List<ResourceDTO>> listResourcesBack(ConditionVO condition) {
        return Result.ok(resourceService.listResourcesBack(condition));
    }

    @ApiOperation(value = "新增或修改资源", notes = "新增或修改资源")
    @OptLog(optType = ADD_OR_UPDATE)
    @PostMapping("/resources")
    public Result<?> addOrUpdateResource(@Valid @RequestBody ResourceVO resourceVO) {
        resourceService.addOrUpdateResource(resourceVO);
        return Result.ok();
    }

    @ApiOperation(value = "删除资源", notes = "删除资源")
    @OptLog(optType = DELETE)
    @ApiImplicitParam(value = "resourceId", name = "资源id", required = true, dataType = "Integer")
    @DeleteMapping("/resources/{resourceId}")
    public Result<?> deleteResource(@PathVariable("resourceId") Integer resourceId) {
        resourceService.deleteResource(resourceId);
        return Result.ok();
    }

    @ApiOperation(value = "查看角色资源选项", notes = "查看角色资源选项")
    @GetMapping("/role/resources")
    public Result<List<LabelOptionDTO>> listRoleResources() {
        return Result.ok(resourceService.listRoleResources());
    }
}
