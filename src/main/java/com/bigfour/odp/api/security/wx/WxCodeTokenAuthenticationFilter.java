package com.bigfour.odp.api.security.wx;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : zhujiwu
 * @date : 2018/11/8.
 */
public class WxCodeTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public WxCodeTokenAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login/wx", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String code = request.getParameter("code");
        if (code == null) {
            code = "";
        }
        code = code.trim();

        WxCodeAuthenticationToken authRequest = new WxCodeAuthenticationToken(code);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, WxCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

}
