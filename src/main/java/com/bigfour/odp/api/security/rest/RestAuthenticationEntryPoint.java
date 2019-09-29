package com.bigfour.odp.api.security.rest;

import com.bigfour.odp.api.response.ErrorInfo;
import com.bigfour.odp.api.response.ResponseJsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问未授权资源处理
 *
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ErrorInfo error = new ErrorInfo("unauthorized", "用户未登录");
        ResponseJsonUtils.response(error, HttpStatus.UNAUTHORIZED, response);
    }
}
