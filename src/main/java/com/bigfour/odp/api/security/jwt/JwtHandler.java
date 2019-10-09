package com.bigfour.odp.api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtHandlerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;

/**
 * @author : zhujiwu
 * @date : 2019/10/8.
 */
public class JwtHandler extends JwtHandlerAdapter<Jwt> implements io.jsonwebtoken.JwtHandler<Jwt> {
    @Override
    public Jwt onClaimsJws(Jws<Claims> jws) {
        Claims body = jws.getBody();
        Instant issuedAt = Instant.ofEpochMilli(body.getIssuedAt().getTime());
        Instant expiresAt = Instant.ofEpochMilli(body.getExpiration().getTime());
        return new Jwt(body.getId(), issuedAt, expiresAt, jws.getHeader(), body);
    }
}
