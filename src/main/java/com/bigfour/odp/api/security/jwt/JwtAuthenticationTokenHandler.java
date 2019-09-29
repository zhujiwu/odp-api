package com.bigfour.odp.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import io.jsonwebtoken.Jwts;

/**
 * @author : zhujiwu
 * @date : 2019/9/27.
 */
public class JwtAuthenticationTokenHandler extends JwtHandlerAdapter<JwtAuthenticationToken> {

    @Override
    public JwtAuthenticationToken onClaimsJws(Jws<Claims> jws) {
        Claims body = jws.getBody();
        return parseJwtAuthenticationToken(body);
    }

    public static Claims parseClaims(JwtAuthenticationToken jwtAuthenticationToken) {
        Claims claims = Jwts.claims();
        claims.setId(jwtAuthenticationToken.getId())
                .setSubject(jwtAuthenticationToken.getSubject())
                .setIssuedAt(jwtAuthenticationToken.getIssuedAt())
                .setExpiration(jwtAuthenticationToken.getExpiration());
        claims.put("userId", jwtAuthenticationToken.getUserId());
        return claims;
    }

    public static JwtAuthenticationToken parseJwtAuthenticationToken(Claims claims) {
        JwtAuthenticationTokenImpl token = new JwtAuthenticationTokenImpl(claims.getSubject(), null);
        token.setId(claims.getId());
        token.setUserId(claims.get("userId", String.class));
        token.setExpiration(claims.getExpiration());
        token.setIssuedAt(claims.getIssuedAt());
        return token;
    }

}
