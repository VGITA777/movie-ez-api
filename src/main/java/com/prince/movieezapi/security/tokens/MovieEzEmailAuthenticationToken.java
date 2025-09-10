package com.prince.movieezapi.security.tokens;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MovieEzEmailAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public MovieEzEmailAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MovieEzEmailAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
