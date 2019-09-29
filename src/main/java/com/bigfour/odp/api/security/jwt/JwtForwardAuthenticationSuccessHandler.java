package com.bigfour.odp.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

/**
 * @author : zhujiwu
 * @date : 2019/9/27.
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class JwtForwardAuthenticationSuccessHandler extends ForwardAuthenticationSuccessHandler {

    private String forwardUrl;

    private Key key;

    @Builder.Default
    private Duration expiration = JwtConsts.DEFAULT_EXPIRATION;

    public JwtForwardAuthenticationSuccessHandler(String forwardUrl) {
        super(forwardUrl);
        this.forwardUrl = forwardUrl;
    }

    public JwtForwardAuthenticationSuccessHandler(String forwardUrl, Key key, Duration expiration) {
        this(forwardUrl);
        this.key = key;
        this.expiration = expiration;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JwtAuthenticationToken jwtAuthenticationToken;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken upaToken = (UsernamePasswordAuthenticationToken) authentication;
            JwtAuthenticationTokenAdapter tokenAdapter = new JwtAuthenticationTokenAdapter(upaToken);
            tokenAdapter.setId(UUID.randomUUID().toString().replace("-", ""));
            jwtAuthenticationToken = tokenAdapter;
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        Claims claims = JwtAuthenticationTokenHandler.parseClaims(jwtAuthenticationToken);
        JwtBuilder builder = Jwts.builder();
        Date curDate = new Date();
        builder.setClaims(claims)
                .setIssuer(JwtConsts.ISSUER)
                .setNotBefore(curDate)
                .setIssuedAt(curDate)
                .setExpiration(new Date(curDate.getTime() + expiration.toMillis()))
                .signWith(key);

        String token = builder.compact();
        response.addHeader(JwtConsts.TOKEN_HEADER_KEY, JwtConsts.TOKEN_PREFIX + token);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
