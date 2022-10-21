package com.wcy.blog.dao;

import com.wcy.blog.dto.UserBackDTO;
import com.wcy.blog.entity.UserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_user_auth】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.UserAuth
*/
public interface UserAuthDao extends BaseMapper<UserAuth> {

    List<UserBackDTO> listUsersBack(@Param("current") Long current,
                                    @Param("size") Long size,
                                    @Param("condition") ConditionVO condition);
}




