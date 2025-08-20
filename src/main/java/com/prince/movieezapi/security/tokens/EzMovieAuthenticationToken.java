package com.prince.movieezapi.security.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class EzMovieAuthenticationToken extends AbstractAuthenticationToken {
    public EzMovieAuthenticationToken() {
        super(Collections.emptyList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return "EzMovie";
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new IllegalArgumentException("Cannot change authenticated state of EzMovieAuthenticationToken");
    }
}
