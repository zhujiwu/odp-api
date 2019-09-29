package com.bigfour.odp.api.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.NonceExpiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;

/**
 * @author : zhujiwu
 * @date : 2018/11/8.
 */
@Data
public class JwtTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private Key key;

    private JwtHandler<JwtAuthenticationToken> jwtHandler = new JwtAuthenticationTokenHandler();

    public JwtTokenAuthenticationFilter() {
        super("/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = request.getHeader(JwtConsts.TOKEN_HEADER_KEY);
        if (token == null) {
            return null;
        }
        if (token.startsWith(JwtConsts.TOKEN_PREFIX)) {
            token = token.substring(JwtConsts.TOKEN_PREFIX.length());
        } else {
            throw new InsufficientAuthenticationException("token 格式不正确");
        }
        JwtAuthenticationToken jwtAuthenticationToken = null;
        try {
            jwtAuthenticationToken = Jwts.parser().setSigningKey(key).parse(token, jwtHandler);
        } catch (ExpiredJwtException e) {
            throw new NonceExpiredException("登录已过期", e);
        } catch (UnsupportedJwtException e) {
            throw new InsufficientAuthenticationException("不支持的凭据", e);
        } catch (MalformedJwtException e) {
            throw new InsufficientAuthenticationException("凭据格式错误", e);
        } catch (SignatureException e) {
            throw new BadCredentialsException("凭证错误", e);
        } catch (IllegalArgumentException e) {
            throw new InsufficientAuthenticationException("参数不正确", e);
        }
        getAuthenticationManager().authenticate(jwtAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
        return jwtAuthenticationToken;
    }
}
