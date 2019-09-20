package com.bigfour.odp.api.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : zhujiwu
 * @date : 2018/11/9.
 */
public class SecurityUtils {

    public static OdpUserDetails getUserDetails() {
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (details instanceof OdpUserDetails) {
            return (OdpUserDetails) details;
        }
        return null;
    }

    public static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }

}
