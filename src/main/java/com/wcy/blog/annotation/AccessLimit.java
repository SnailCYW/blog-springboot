package com.wcy.blog.annotation;

import java.lang.annotation.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/22/11:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();

}
