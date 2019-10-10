package com.bigfour.odp.api.controller;

import com.bigfour.odp.api.security.OdpUserDetails;
import com.bigfour.odp.api.security.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author : zhujiwu
 * @date : 2019/9/20.
 */
@RestController
@RequestMapping("/passport")
public class PassportController {
    @GetMapping("/info")
    public OdpUserDetails info() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken){
            JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
            String name = token.getName();
            Map<String, Object> tokenAttributes = token.getTokenAttributes();
            System.out.println(tokenAttributes);
        }

        OdpUserDetails userDetails = null;//SecurityUtils.getUserDetails();
        return userDetails;
    }
}
