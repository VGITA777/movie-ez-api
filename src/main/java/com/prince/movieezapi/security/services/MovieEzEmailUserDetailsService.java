package com.prince.movieezapi.security.services;

import com.prince.movieezapi.security.authprovider.MovieEzEmailAuthenticationProvider;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Used by {@link MovieEzEmailAuthenticationProvider} to find users using their email.
 */
@Primary
@Service
public class MovieEzEmailUserDetailsService implements UserDetailsService {

    private final MovieEzUserService userService;

    public MovieEzEmailUserDetailsService(MovieEzUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
