package com.prince.movieezapi.security.tokens;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Custom authentication token for username-based authentication.
 * This token is used to carry username credentials through the authentication process.
 */
public class MovieEzUsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {

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

    public static MovieEzUsernamePasswordAuthenticationToken authenticated(Object principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        return new MovieEzUsernamePasswordAuthenticationToken(principal, credentials, authorities);
    }

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
