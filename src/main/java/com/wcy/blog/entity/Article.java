package com.wcy.blog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName tb_article
 */
@TableName(value ="tb_article")
@Data
public class Article implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 作者
     */
    private Integer userId;

    /**
     * 文章分类
     */
    private Integer categoryId;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 内容
     */
    private String articleContent;

    /**
     * 文章类型 1原创 2转载 3翻译
     */
    private Integer type;

    /**
     * 原文链接
     */
    private String originalUrl;

    /**
     * 是否置顶 0否 1是
     */
    private Integer isTop;

    /**
     * 是否删除  0否 1是
     */
    private Integer isDelete;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    private Integer status;

    /**
     * 发表时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}