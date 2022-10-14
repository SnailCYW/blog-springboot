package com.wcy.blog.dao;

import com.wcy.blog.dto.TagBackDTO;
import com.wcy.blog.dto.TagDTO;
import com.wcy.blog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_tag】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.Tag
*/
public interface TagDao extends BaseMapper<Tag> {

    public List<TagBackDTO> selectBackList(@Param("current") Long current,
                                           @Param("size") Long size,
                                           @Param("condition") ConditionVo condition);

    public List<TagDTO> searchList(@Param("condition") ConditionVo condition);
}




