package com.bigfour.odp.api.security.jwt;

import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * @author : zhujiwu
 * @date : 2019/9/29.
 */
public interface JwtAuthenticationToken extends Authentication {

    String getId();

    String getUserId();

    String getSubject();

    Date getIssuedAt();

    Date getExpiration();

//    void setDetails(Object details);

}
