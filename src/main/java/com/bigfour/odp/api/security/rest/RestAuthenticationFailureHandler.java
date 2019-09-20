package com.bigfour.odp.api.security.rest;

import com.bigfour.odp.api.response.ErrorInfo;
import com.bigfour.odp.api.response.ResponseJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
@Slf4j
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ErrorInfo error;
        if (e instanceof BadCredentialsException) {
            error = new ErrorInfo("username_or_password_error", "用户名或密码错误");
        } else {
            log.error("登录失败", e);
            error = new ErrorInfo("auth_failure", "登录失败");
        }

        ResponseJsonUtils.response(error, HttpStatus.BAD_REQUEST, response);
    }
}
