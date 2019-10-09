package com.bigfour.odp.api.security.jwt;

import io.jsonwebtoken.Jwts;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;

import java.security.Key;

/**
 * @author : zhujiwu
 * @date : 2019/10/8.
 */
@Data
@Builder
public class JwtDecoder implements org.springframework.security.oauth2.jwt.JwtDecoder {

    private Key publicKey;

    private JwtHandler jwtHandler;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            return Jwts.parser().setSigningKey(publicKey).parse(token, jwtHandler);
        } catch (io.jsonwebtoken.JwtException e) {
            throw new JwtException(e.getMessage(), e);
        }
    }
}
