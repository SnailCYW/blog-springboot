package com.wcy.blog.dao;

import com.wcy.blog.dto.CommentBackDTO;
import com.wcy.blog.dto.CommentDTO;
import com.wcy.blog.dto.ReplyCountDTO;
import com.wcy.blog.dto.ReplyDTO;
import com.wcy.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wcy.blog.vo.CommentVO;
import com.wcy.blog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_comment】的数据库操作Mapper
* @createDate 2022-09-25 22:58:52
* @Entity com.wcy.blog.entity.Comment
*/
public interface CommentDao extends BaseMapper<Comment> {

    Integer getCountBack(@Param("condition") ConditionVO condition);

    List<CommentBackDTO> listCommentBack(@Param("current") Long current,
                                         @Param("size") Long size,
                                         @Param("condition") ConditionVO condition);

    List<CommentDTO> listComments(@Param("current") Long current,
                                  @Param("size") Long size,
                                  @Param("commentVO") CommentVO commentVO);

    List<ReplyDTO> listReplies(List<Integer> commentIdList);

    List<ReplyCountDTO> listReplyCountByCommentId(@Param("commentIdList") List<Integer> commentIdList);

    List<ReplyDTO> listAllRepliesByCommentId(@Param("current") Long current,
                                             @Param("size") Long size,
                                             @Param("commentId") Integer commentId);
}




