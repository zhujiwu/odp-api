package com.bigfour.odp.api.security.jwt;

import java.time.Duration;

/**
 * JWT常量定义
 *
 * @author : zhujiwu
 * @date : 2019/9/26.
 */
public abstract class JwtConsts {
    public static final String TOKEN_HEADER_KEY = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ISSUER = "odp-api";

    /**
     * 默认过期时间
     */
    public final static Duration DEFAULT_EXPIRATION = Duration.ofMinutes(30);
}
