package com.prince.movieezapi.security.authenticationtokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

/**
 * Custom authentication token for username-based authentication.
 */
public class MovieEzUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Object principal;
    private final Object credentials;

    /**
     * Creates an unauthenticated token with the provided principal and credentials.
     *
     * @param principal   the username (Or any other identifier) of the user
     * @param credentials the password to authenticate with
     */
    public MovieEzUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    /**
     * Creates an authenticated token with the provided principal, credentials and authorities.
     *
     * @param principal   the username (Or any other identifier) of the user
     * @param credentials the password to authenticate with
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     */
    public MovieEzUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    /**
     * Creates an unauthenticated token.
     *
     * @param principal   the username of the user.
     * @param credentials the password of the user
     * @return an unauthenticated {@link MovieEzUsernamePasswordAuthenticationToken}
     */
    public static MovieEzUsernamePasswordAuthenticationToken unauthenticated(Object principal, String credentials) {
        return new MovieEzUsernamePasswordAuthenticationToken(principal, credentials);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
