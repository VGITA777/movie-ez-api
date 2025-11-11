package com.prince.movieezapi.security.authenticationtokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;

/**
 * Custom authentication token representing a guest user.
 */
public class MovieEzGuestAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Creates a fully authenticated guest user.
     */
    public MovieEzGuestAuthenticationToken() {
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
        throw new IllegalArgumentException("Cannot change authenticated state of MovieEzGuestAuthenticationToken");
    }
}
