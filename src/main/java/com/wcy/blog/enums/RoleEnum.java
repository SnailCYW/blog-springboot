package com.wcy.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 角色枚举
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/9:19
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {
    /**
     * 管理员
     */
    ADMIN(1, "管理员", "admin"),
    /**
     * 普通用户
     */
    USER(2, "用户", "user"),
    /**
     * 测试账号
     */
    TEST(3, "测试", "test");

    /**
     * 角色id
     */
    private final Integer roleId;

    /**
     * 描述
     */
    private final String name;

    /**
     * 权限标签
     */
    private final String label;

}