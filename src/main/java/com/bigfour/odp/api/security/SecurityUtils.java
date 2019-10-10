package com.bigfour.odp.api.security;

import com.bigfour.odp.api.security.jwt.JwtConsts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/**
 * @author : zhujiwu
 * @date : 2018/11/9.
 */
public class SecurityUtils {

    public static String getUserId() {
        Authentication authentication = getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            Object userId = token.getTokenAttributes().get(JwtConsts.USER_ID_KEY);
            return (String) userId;
        }
        return null;
    }

    public static Authentication getAuthentication() {
        Authentication authentication = getContext().getAuthentication();
        return authentication;
    }


    public static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

}
