package com.bigfour.odp.api.security.jwt;

import com.bigfour.odp.api.security.OdpUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 登录成功后响应token
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

    private final Converter<UsernamePasswordAuthenticationToken, Claims> bearerTokenConverter = authenticationToken -> {
        String tokenId = UUID.randomUUID().toString().replace("-", "");
        OdpUserDetails principal = (OdpUserDetails) authenticationToken.getPrincipal();
        String userId = principal.getUserId();
        Date curDate = new Date();

        Claims claims = Jwts.claims();
        claims.setId(tokenId)
                .setIssuer(JwtConsts.ISSUER)
                .setNotBefore(curDate)
                .setIssuedAt(curDate)
                .setExpiration(new Date(curDate.getTime() + expiration.toMillis()))
                .setSubject(authenticationToken.getName());
        claims.put("userId", userId);
        List<String> authorities = authenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        claims.put(JwtConsts.SCOPE_KEY,authorities);
        return claims;
    };

    public JwtForwardAuthenticationSuccessHandler(String forwardUrl, Key key, Duration expiration) {
        super(forwardUrl);
        this.forwardUrl = forwardUrl;
        this.key = key;
        this.expiration = expiration;
    }

    public JwtForwardAuthenticationSuccessHandler(String forwardUrl) {
        super(forwardUrl);
        this.forwardUrl = forwardUrl;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Claims claims;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;
            claims = bearerTokenConverter.convert(authenticationToken);
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }

        String bearerToken = Jwts.builder().setClaims(claims).signWith(key).compact();
        response.addHeader(JwtConsts.TOKEN_HEADER_KEY, JwtConsts.TOKEN_PREFIX + bearerToken);
        super.onAuthenticationSuccess(request, response, authentication);
    }


}
