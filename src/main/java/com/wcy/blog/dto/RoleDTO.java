package com.wcy.blog.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wcy.blog.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/28/15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleDTO {

    private LocalDateTime createTime;

    private Integer id;

    private Integer isDisable;

    private List<Integer> menuIdList;

    private List<Integer> resourceIdList;

    private String roleLabel;

    private String roleName;

}
