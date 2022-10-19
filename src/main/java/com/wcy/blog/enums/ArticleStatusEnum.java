package com.wcy.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/18/19:22
 */
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {

    PUBLIC(1, "公开"),

    PRIVATE(2, "私密"),

    DRAFT(3, "草稿");

    private final Integer status;

    private final String desc;

}
