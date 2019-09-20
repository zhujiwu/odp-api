package com.bigfour.odp.api.security.wx;

import com.bigfour.odp.api.security.OdpUserDetails;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

/**
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
public class WxAuthenticationProvider implements AuthenticationProvider {
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OdpUserDetails userDetails = (OdpUserDetails) userDetailsService.loadUserByUsername(authentication.getName());
        if (userDetails == null) {
            throw new UsernameNotFoundException("code验证失败");
        }
        if (!userDetails.isEnabled()) {
            throw new DisabledException("账号被禁用");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        WxCodeAuthenticationToken authenticationToken = new WxCodeAuthenticationToken(userDetails.getUsername(), Arrays.asList(authority));
        authenticationToken.setAuthenticated(true);
        authenticationToken.setDetails(userDetails);
        return authenticationToken;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return WxCodeAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
