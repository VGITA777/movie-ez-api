package com.prince.movieezapi.security.authprovider;

import com.prince.movieezapi.security.services.MovieEzUsernameUserDetailsService;
import com.prince.movieezapi.security.tokens.MovieEzUsernameAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MovieEzUsernameAuthenticationProvider extends MovieEzAuthenticationProvider {
    public MovieEzUsernameAuthenticationProvider(MovieEzUsernameUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(userDetailsService, passwordEncoder);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MovieEzUsernameAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
