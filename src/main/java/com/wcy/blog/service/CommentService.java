package com.wcy.blog.service;

import com.wcy.blog.dto.CommentBackDTO;
import com.wcy.blog.dto.CommentDTO;
import com.wcy.blog.dto.ReplyDTO;
import com.wcy.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.blog.vo.CommentVO;
import com.wcy.blog.vo.ConditionVO;
import com.wcy.blog.vo.PageResult;
import com.wcy.blog.vo.ReviewVO;

import java.util.List;

/**
* @author Snail
* @description 针对表【tb_comment】的数据库操作Service
* @createDate 2022-09-25 22:59:17
*/
public interface CommentService extends IService<Comment> {

    PageResult<CommentBackDTO> listCommentBack(ConditionVO condition);

    void deleteComment(List<Integer> commentIdList);

    void reviewComment(ReviewVO reviewVO);

    PageResult<CommentDTO> listComment(CommentVO commentVO);

    void addComment(CommentVO commentVO);

    void likeComment(Integer commentId);

    List<ReplyDTO> listCommentByParentId(Integer commentId);
}
