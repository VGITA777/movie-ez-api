package com.prince.movieezapi.security.authprovider;

import com.prince.movieezapi.security.authenticationtokens.MovieEzEmailPasswordAuthenticationToken;
import com.prince.movieezapi.security.services.MovieEzEmailUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * An authentication provider in charge of authenticating {@link MovieEzEmailPasswordAuthenticationToken}
 */
@Service
public class MovieEzEmailAuthenticationProvider extends MovieEzAuthenticationProvider {
    public MovieEzEmailAuthenticationProvider(MovieEzEmailUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(userDetailsService, passwordEncoder);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MovieEzEmailPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
