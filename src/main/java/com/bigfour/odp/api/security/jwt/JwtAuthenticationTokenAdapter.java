package com.bigfour.odp.api.security.jwt;

import com.bigfour.odp.api.security.OdpUserDetails;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

/**
 * @author : zhujiwu
 * @date : 2019/9/27.
 */
@Getter
@Setter(AccessLevel.PACKAGE)
public class JwtAuthenticationTokenAdapter implements JwtAuthenticationToken {

    private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

    private String id;

    public JwtAuthenticationTokenAdapter(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        this.usernamePasswordAuthenticationToken = usernamePasswordAuthenticationToken;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUserId() {
        return getUserDetails().getUserId();
    }

    @Override
    public String getSubject() {
        return getUserDetails().getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usernamePasswordAuthenticationToken.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return usernamePasswordAuthenticationToken.getCredentials();
    }

    @Override
    public Object getDetails() {
        return usernamePasswordAuthenticationToken.getDetails();
    }

    @Override
    public Object getPrincipal() {
        return getUserDetails().getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return usernamePasswordAuthenticationToken.isAuthenticated();
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        usernamePasswordAuthenticationToken.setAuthenticated(b);
    }

    @Override
    public Date getIssuedAt() {
        return null;
    }

    @Override
    public Date getExpiration() {
        return null;
    }

    @Override
    public String getName() {
        return usernamePasswordAuthenticationToken.getName();
    }

    protected OdpUserDetails getUserDetails() {
        return (OdpUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
    }
}
