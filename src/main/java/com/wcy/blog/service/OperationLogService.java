package com.wcy.blog.service;

import com.wcy.blog.dto.OperationLogDTO;
import com.wcy.blog.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_operation_log】的数据库操作Service
* @createDate 2022-09-25 22:59:18
*/
public interface OperationLogService extends IService<OperationLog> {

    void deleteOperationLogs(List<Integer> logIdList);

    PageResult<OperationLogDTO> listOperationLogs(ConditionVO condition);
}
