package com.prince.movieezapi.security.authprovider;

import com.prince.movieezapi.security.authenticationtokens.MovieEzFullyAuthenticatedUser;
import com.prince.movieezapi.security.userdetails.MovieEzEmailUserDetailsService;
import com.prince.movieezapi.security.userdetails.MovieEzUsernameUserDetailsService;
import com.prince.movieezapi.shared.models.UserIdentifierModel;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ott.InvalidOneTimeTokenException;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MovieEzOneTimeTokenAuthenticationProvider implements AuthenticationProvider {

    private final OneTimeTokenService oneTimeTokenService;
    private final MovieEzUsernameUserDetailsService usernameUserDetailsService;
    private final MovieEzEmailUserDetailsService emailUserDetailsService;
    private final BasicUtils basicUtils;

    public MovieEzOneTimeTokenAuthenticationProvider(OneTimeTokenService oneTimeTokenService, MovieEzUsernameUserDetailsService usernameUserDetailsService, MovieEzEmailUserDetailsService emailUserDetailsService, BasicUtils basicUtils) {
        this.oneTimeTokenService = oneTimeTokenService;
        this.usernameUserDetailsService = usernameUserDetailsService;
        this.emailUserDetailsService = emailUserDetailsService;
        this.basicUtils = basicUtils;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OneTimeTokenAuthenticationToken oneTimeTokenAuthenticationToken = (OneTimeTokenAuthenticationToken) authentication;
        var consumedToken = oneTimeTokenService.consume(oneTimeTokenAuthenticationToken);

        if (consumedToken == null) {
            log.warn("Invalid One Time Token provided for token ID '{}'", oneTimeTokenAuthenticationToken.getTokenValue());
            throw new InvalidOneTimeTokenException("Invalid One Time Token provided");
        }

        var userDetails = basicUtils.isValidEmail(consumedToken.getUsername()) ?
                emailUserDetailsService.loadUserByUsername(consumedToken.getUsername()) :
                usernameUserDetailsService.loadUserByUsername(consumedToken.getUsername());
        var userIdentifier = UserIdentifierModel.of(userDetails);
        return new MovieEzFullyAuthenticatedUser(userIdentifier, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OneTimeTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
