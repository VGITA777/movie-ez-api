package com.prince.movieezapi.security.services;

import com.prince.movieezapi.user.services.MovieEzUserService;
import lombok.val;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OneTimeTokenUserDetailsService implements UserDetailsService {

    private final OneTimeTokenRepositoryService oneTimeTokenRepositoryService;
    private final MovieEzUserService userService;

    public OneTimeTokenUserDetailsService(OneTimeTokenRepositoryService oneTimeTokenRepositoryService, MovieEzUserService userService) {
        this.oneTimeTokenRepositoryService = oneTimeTokenRepositoryService;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        val oneTimeTokenModel = oneTimeTokenRepositoryService.findByToken(token).orElseThrow(() -> new UsernameNotFoundException("User not found with token: '" + token + "'"));
        val username = oneTimeTokenModel.getUsername();

        return null;
    }
}
