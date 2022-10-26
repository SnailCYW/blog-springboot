package com.wcy.blog.dao;

import com.wcy.blog.dto.TalkBackDTO;
import com.wcy.blog.dto.TalkDTO;
import com.wcy.blog.entity.Talk;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_talk】的数据库操作Mapper
* @createDate 2022-09-25 22:58:53
* @Entity com.wcy.blog.entity.Talk
*/
public interface TalkDao extends BaseMapper<Talk> {

    List<TalkBackDTO> listTalksBack(@Param("current") Long current,
                                    @Param("size") Long size,
                                    @Param("condition") ConditionVO condition);

    TalkBackDTO listTalksBackById(@Param("talkId") Integer talkId);

    List<TalkDTO> listTalks(@Param("current") Long current,
                            @Param("size") Long size);

    TalkDTO listTalkById(@Param("talkId") Integer talkId);
}




