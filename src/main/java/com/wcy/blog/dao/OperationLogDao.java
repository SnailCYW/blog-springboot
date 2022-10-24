package com.wcy.blog.dao;

import com.wcy.blog.dto.OperationLogDTO;
import com.wcy.blog.entity.OperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import org.apache.ibatis.annotations.Param;

/**
* @author Snail
* @description 针对表【tb_operation_log】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.OperationLog
*/
public interface OperationLogDao extends BaseMapper<OperationLog> {

    PageResult<OperationLogDTO> listOperationLogs(@Param("current") Long current,
                                                  @Param("size") Long size,
                                                  @Param("condition") ConditionVO condition);
}




