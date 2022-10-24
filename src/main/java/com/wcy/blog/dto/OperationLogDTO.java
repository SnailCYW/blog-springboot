package com.wcy.blog.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/21:58
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogDTO {
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 操作ip
     */
    private String ipAddress;

    /**
     * 操作地址
     */
    private String ipSource;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 操作描述
     */
    private String optDesc;

    /**
     * 操作方法
     */
    private String optMethod;

    /**
     * 操作模块
     */
    private String optModule;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 操作url
     */
    private String optUrl;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回数据
     */
    private String responseData;

}
