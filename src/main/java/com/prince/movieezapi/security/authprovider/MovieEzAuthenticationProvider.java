package com.prince.movieezapi.security.authprovider;

import com.prince.movieezapi.security.authenticationtokens.MovieEzFullyAuthenticatedUser;
import com.prince.movieezapi.shared.models.UserIdentifierModel;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * A base class used for authenticating tokens.
 */
@Slf4j
public abstract class MovieEzAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public MovieEzAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        log.info("Authenticating user: {}", authentication.getName());


        MovieEzUserModel searchResult = (MovieEzUserModel) userDetailsService.loadUserByUsername(authentication.getName());

        if (searchResult == null) {
            throw new UsernameNotFoundException("User not found: " + authentication.getName());
        }

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), searchResult.getPassword())) {
            throw new BadCredentialsException("Invalid password for user: " + authentication.getName());
        }

        log.info("Authentication successful for user: {}", authentication.getName());
        eraseCredentials(searchResult);
        UserIdentifierModel userIdentifier = new UserIdentifierModel(searchResult.getId(), searchResult.getUsername(), searchResult.getEmail());
        return new MovieEzFullyAuthenticatedUser(userIdentifier, searchResult.getAuthorities());
    }

    private void eraseCredentials(UserDetails userDetails) {
        if (userDetails instanceof CredentialsContainer credentialsContainer) {
            credentialsContainer.eraseCredentials();
        }
    }
}
