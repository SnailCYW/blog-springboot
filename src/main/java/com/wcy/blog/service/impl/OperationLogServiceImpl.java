package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.dto.OperationLogDTO;
import com.wcy.blog.entity.OperationLog;
import com.wcy.blog.service.OperationLogService;
import com.wcy.blog.dao.OperationLogDao;
import com.wcy.blog.util.BeanCopyUtils;
import com.wcy.blog.util.PageUtils;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author Snail
 * @description 针对表【tb_operation_log】的数据库操作Service实现
 * @createDate 2022-09-25 22:59:18
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLog>
        implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public void deleteOperationLogs(List<Integer> logIdList) {
        operationLogDao.deleteBatchIds(logIdList);
    }

    @Override
    public PageResult<OperationLogDTO> listOperationLogs(ConditionVO condition) {
        Page<OperationLog> page = new Page<>(PageUtils.getLimitCurrent(), PageUtils.getSize());
        LambdaQueryWrapper<OperationLog> queryWrapper = new LambdaQueryWrapper<OperationLog>()
                .like(Objects.nonNull(condition.getKeywords()), OperationLog::getOptModule, condition.getKeywords())
                .or()
                .like(Objects.nonNull(condition.getKeywords()), OperationLog::getOptDesc, condition.getKeywords())
                .orderByDesc(OperationLog::getId);
        Page<OperationLog> operationLogPage = operationLogDao.selectPage(page, queryWrapper);
        return new PageResult<>((int)operationLogPage.getTotal(), BeanCopyUtils.copyList(operationLogPage.getRecords(), OperationLogDTO.class));
    }
}




