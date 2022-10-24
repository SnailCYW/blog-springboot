package com.wcy.blog.controller.admin;

import com.wcy.blog.dto.OperationLogDTO;
import com.wcy.blog.entity.Page;
import com.wcy.blog.service.OperationLogService;
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
 * @Date: 2022/10/24/21:55
 */
@RestController
@RequestMapping("/admin/operation/logs")
@Api(tags = "日志模块")
public class LogsAdminController {

    @Autowired
    private OperationLogService operationLogService;

    @ApiOperation(value = "查看操作日志", notes = "查看操作日志")
    @GetMapping
    public Result<PageResult<OperationLogDTO>> listOperationLogs(ConditionVO condition) {
        return Result.ok(operationLogService.listOperationLogs(condition));
    }

    @ApiOperation(value = "删除操作日志", notes = "删除操作日志")
    @ApiImplicitParam(name = "logIdList", value = "操作日志id", required = true, dataType = "List<Integer>")
    @DeleteMapping
    public Result<?> deleteOperationLogs(@RequestBody List<Integer> logIdList) {
        operationLogService.deleteOperationLogs(logIdList);
        return Result.ok();
    }

}
