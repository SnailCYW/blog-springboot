package com.wcy.blog.dao;

import cn.hutool.core.date.DateTime;
import com.wcy.blog.dto.UniqueViewDTO;
import com.wcy.blog.entity.UniqueView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @author Snail
* @description 针对表【tb_unique_view】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.UniqueView
*/
public interface UniqueViewDao extends BaseMapper<UniqueView> {

    List<UniqueViewDTO> listUniqueViews(@Param("startTime") Date startTime,
                                        @Param("endTime") Date endTime);
}




