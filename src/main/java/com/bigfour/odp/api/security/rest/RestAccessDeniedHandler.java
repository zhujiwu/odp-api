package com.bigfour.odp.api.security.rest;

import com.bigfour.odp.api.response.ErrorInfo;
import com.bigfour.odp.api.response.ResponseJsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ErrorInfo error = new ErrorInfo("forbidden", "权限不足");
        ResponseJsonUtils.response(error, HttpStatus.FORBIDDEN, response);
    }
}
