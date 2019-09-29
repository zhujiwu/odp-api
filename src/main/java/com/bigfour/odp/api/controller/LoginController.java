package com.bigfour.odp.api.controller;

import com.bigfour.odp.api.response.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : zhujiwu
 * @date : 2019/9/23.
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("success")
    public String success() {
        return "success";
    }

    @RequestMapping("failure")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo failure(HttpServletRequest request) {
        AuthenticationException e = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        ErrorInfo error;
        if (e instanceof BadCredentialsException) {
            error = new ErrorInfo("username_or_password_error", "用户名或密码错误");
        } else if (e instanceof NonceExpiredException) {
            error = new ErrorInfo("nonce_expired", "会话过期");
        } else {
            log.error("登录失败", e);
            error = new ErrorInfo("auth_failure", "登录失败");
        }
        return error;
    }
}
