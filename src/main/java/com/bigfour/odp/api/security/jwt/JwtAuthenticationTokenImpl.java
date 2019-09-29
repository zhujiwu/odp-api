package com.bigfour.odp.api.security.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * @author : zhujiwu
 * @date : 2019/9/26.
 */
@Getter
@Setter(AccessLevel.PACKAGE)
public class JwtAuthenticationTokenImpl extends AbstractAuthenticationToken implements JwtAuthenticationToken {
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String subject;

    private Date expiration;

    private Date issuedAt;

    public JwtAuthenticationTokenImpl(String subject, Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.subject = subject;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return subject;
    }

}
