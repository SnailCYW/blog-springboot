package com.wcy.blog.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wcy.blog.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/25/10:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageBackDTO {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 留言内容
     */
    private String messageContent;

    /**
     * 用户ip
     */
    private String ipAddress;

    /**
     * 用户地址
     */
    private String ipSource;

    /**
     * 是否审核
     */
    private Integer isReview;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;


}
