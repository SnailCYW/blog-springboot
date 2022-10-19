package com.wcy.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/19/12:01
 */
@Getter
@AllArgsConstructor
public enum SearchModeEnum {

    /**
     * mysql
     */
    MYSQL("mysql", "MysqlSearchStrategyImpl"),
    /**
     * elasticsearch
     */
    ELASTICSEARCH("elasticsearch", "esSearchStrategyImpl");

    /**
     * 模式
     */
    private final String mode;

    /**
     * 策略
     */
    private final String strategy;

    /**
     * 获取策略
     *
     * @param mode 模式
     * @return {@link String} 搜索策略
     */
    public static String getStrategy(String mode) {
        for (SearchModeEnum value : SearchModeEnum.values()) {
            if (value.getMode().equals(mode)) {
                return value.getStrategy();
            }
        }
        return null;
    }

}
