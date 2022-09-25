package com.wcy.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.blog.entity.OperationLog;
import com.wcy.blog.service.OperationLogService;
import com.wcy.blog.dao.OperationLogDao;
import org.springframework.stereotype.Service;

/**
* @author Snail
* @description 针对表【tb_operation_log】的数据库操作Service实现
* @createDate 2022-09-25 22:59:18
*/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLog>
    implements OperationLogService{

}




