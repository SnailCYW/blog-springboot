package com.wcy.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author WuChongYuan
 * @version 1.0
 * @date 2022/9/25 22:14
 */

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS(20000, "操作成功"),

    FAIL(51000, "操作失败");

    private final Integer code;

    private final String desc;

}
