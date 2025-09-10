package com.prince.movieezapi.security.authprovider;

import com.prince.movieezapi.security.services.MovieEzEmailUserDetailsService;
import com.prince.movieezapi.security.tokens.MovieEzEmailAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MovieEzEmailAuthenticationProvider extends MovieEzAuthenticationProvider {
    public MovieEzEmailAuthenticationProvider(MovieEzEmailUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(userDetailsService, passwordEncoder);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MovieEzEmailAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    protected AbstractAuthenticationToken createAuthenticatedToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        return new MovieEzEmailAuthenticationToken(principal, null, authorities);
    }
}
