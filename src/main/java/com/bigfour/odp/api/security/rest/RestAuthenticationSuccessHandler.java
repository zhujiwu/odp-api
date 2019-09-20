package com.bigfour.odp.api.security.rest;

import com.bigfour.odp.api.response.ResponseJsonUtils;
import org.springframework.data.rest.core.util.MapUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.MultiValueMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String,String> json = new HashMap<>(2);
        json.put("token",generateNewToken(request));
        ResponseJsonUtils.success(json, response);
    }

    private String generateNewToken(HttpServletRequest request) {
        String token = UUID.randomUUID().toString().replace("-", "");
        request.setAttribute(RestConsts.HEADER_TOKEN_KEY, token);
        return token;
    }
}
