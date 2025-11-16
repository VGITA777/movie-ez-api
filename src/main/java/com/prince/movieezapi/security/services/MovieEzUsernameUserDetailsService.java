package com.prince.movieezapi.security.services;

import com.prince.movieezapi.security.authprovider.MovieEzUsernameAuthenticationProvider;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Used by {@link MovieEzUsernameAuthenticationProvider} to find users using their username.
 */
@Service
public class MovieEzUsernameUserDetailsService implements UserDetailsService {

    private final MovieEzUserService userService;

    public MovieEzUsernameUserDetailsService(MovieEzUserService userService) {
        this.userService = userService;
    }

    @Override
    public MovieEzUserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }
}
