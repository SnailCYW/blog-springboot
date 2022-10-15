package com.wcy.blog.exception;

import com.wcy.blog.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.wcy.blog.enums.StatusCodeEnum.*;

/**
 * 业务异常
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/14/15:08
 */

@Getter
@AllArgsConstructor
public class BizException extends RuntimeException{

    private Integer code = FAIL.getCode();

    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }

}
