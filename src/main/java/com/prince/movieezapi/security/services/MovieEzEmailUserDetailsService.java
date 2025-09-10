package com.prince.movieezapi.security.services;

import com.prince.movieezapi.shared.utilities.UserSecurityUtils;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MovieEzEmailUserDetailsService implements UserDetailsService {

    private final MovieEzUserService userService;

    public MovieEzEmailUserDetailsService(MovieEzUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MovieEzUserModel movieEzUserModel = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        return UserSecurityUtils.generateUserDetails(movieEzUserModel.getEmail(), movieEzUserModel.getPassword());
    }
}
