package com.bigfour.odp.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @author : zhujiwu
 * @date : 2018/11/8.
 */
@Slf4j
public class RedisSecurityContextRepository implements SecurityContextRepository {

    @Autowired
    private RedisTemplate<String, SecurityContext> serializationRedisTemplate;

    @Override
    public SecurityContext loadContext(HttpRequestResponseHolder httpRequestResponseHolder) {
        HttpServletRequest request = httpRequestResponseHolder.getRequest();
        SecurityContext securityContext = null;
        String token = getToken(request);
        if (token != null) {
            securityContext = serializationRedisTemplate.opsForValue().get(token);
        }
        if (securityContext == null) {
            securityContext = generateNewContext();
        }
        return securityContext;
    }

    @Override
    public void saveContext(SecurityContext securityContext, HttpServletRequest request, HttpServletResponse response) {
        String token = getToken(request);
        if (token != null) {
            serializationRedisTemplate.opsForValue().set(token, securityContext, 2, TimeUnit.HOURS);
        }
    }


    @Override
    public boolean containsContext(HttpServletRequest request) {
        return getToken(request) != null;
    }

    public String getToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null){
            return null;
        }
        return session.getId();
    }

    protected SecurityContext generateNewContext() {
        return SecurityContextHolder.createEmptyContext();
    }
}
