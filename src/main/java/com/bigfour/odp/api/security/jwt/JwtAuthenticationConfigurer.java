package com.bigfour.odp.api.security.jwt;

import io.jsonwebtoken.JwtHandler;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.web.authentication.ForwardAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.security.Key;

/**
 * @author : zhujiwu
 * @date : 2019/9/29.
 */
public class JwtAuthenticationConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractAuthenticationFilterConfigurer<H, JwtAuthenticationConfigurer<H>, JwtTokenAuthenticationFilter> {

    private JwtTokenAuthenticationFilter authenticationFilter;

    public JwtAuthenticationConfigurer() {
        super(new JwtTokenAuthenticationFilter(), "/**");
        authenticationFilter = getAuthenticationFilter();
    }

    @Override
    public void init(H http) throws Exception {
        http.addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        super.init(http);
    }

    public JwtAuthenticationConfigurer<H> key(Key key) {
        authenticationFilter.setKey(key);
        return this;
    }

    public JwtAuthenticationConfigurer<H> jwtHandler(JwtHandler<JwtAuthenticationToken> jwtHandler) {
        authenticationFilter.setJwtHandler(jwtHandler);
        return this;
    }

    public JwtAuthenticationConfigurer<H> failureForwardUrl(String failureForwardUrl) {
        failureHandler(new ForwardAuthenticationFailureHandler(failureForwardUrl));
        return this;
    }

    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, null);
    }
}
