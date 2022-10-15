package com.wcy.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/16:15
 */

@Getter
@AllArgsConstructor
public enum ZoneEnum {

    SHANGHAI("Asia/Shanghai", "中国上海");

    private final String zone;

    private final String desc;

}
