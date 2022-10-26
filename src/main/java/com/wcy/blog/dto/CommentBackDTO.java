package com.wcy.blog.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wcy.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/25/11:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentBackDTO {

    private String articleTitle;

    private String avatar;

    private String commentContent;

    private LocalDateTime createTime;

    private Integer id;

    private Integer isReview;

    private String nickname;

    private String replyNickname;

    private Integer type;

}
