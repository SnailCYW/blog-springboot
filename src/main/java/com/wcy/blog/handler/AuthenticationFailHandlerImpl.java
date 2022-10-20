package com.wcy.blog.handler;

import com.alibaba.fastjson.JSON;
import com.wcy.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.wcy.blog.constant.CommonConst.APPLICATION_JSON;

/**
 * 登录失败处理
 *
 * @author 吴崇远
 * @version 1.0
 * @Date: 2022/10/20/10:45
 */
@Slf4j
@Component
public class AuthenticationFailHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail(e.getMessage())));
        log.info("message: "+e.getMessage());
    }
}
