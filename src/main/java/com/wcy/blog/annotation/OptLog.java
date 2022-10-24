package com.wcy.blog.annotation;

import java.lang.annotation.*;

/**
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/24/21:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    /**
     * @return 操作类型
     */
    String optType() default "";

}
