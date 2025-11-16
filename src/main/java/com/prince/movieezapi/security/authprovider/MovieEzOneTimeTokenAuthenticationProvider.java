package com.prince.movieezapi.security.authprovider;

import com.prince.movieezapi.security.authenticationtokens.MovieEzFullyAuthenticatedUser;
import com.prince.movieezapi.security.services.MovieEzEmailUserDetailsService;
import com.prince.movieezapi.security.services.MovieEzUsernameUserDetailsService;
import com.prince.movieezapi.shared.models.UserIdentifierModel;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.regex.email:/^[\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$/gm}")
    private String emailRegex;
    private final OneTimeTokenService oneTimeTokenService;
    private final MovieEzUsernameUserDetailsService usernameUserDetailsService;
    private final MovieEzEmailUserDetailsService emailUserDetailsService;

    public MovieEzOneTimeTokenAuthenticationProvider(OneTimeTokenService oneTimeTokenService, MovieEzUsernameUserDetailsService usernameUserDetailsService, MovieEzEmailUserDetailsService emailUserDetailsService) {
        this.oneTimeTokenService = oneTimeTokenService;
        this.usernameUserDetailsService = usernameUserDetailsService;
        this.emailUserDetailsService = emailUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OneTimeTokenAuthenticationToken oneTimeTokenAuthenticationToken = (OneTimeTokenAuthenticationToken) authentication;
        val consumedToken = oneTimeTokenService.consume(oneTimeTokenAuthenticationToken);

        if (consumedToken == null) {
            log.warn("Invalid One Time Token provided for token ID '{}'", oneTimeTokenAuthenticationToken.getTokenValue());
            throw new InvalidOneTimeTokenException("Invalid One Time Token provided");
        }

        val userDetails = isStringEmail(consumedToken.getUsername()) ?
                emailUserDetailsService.loadUserByUsername(consumedToken.getUsername()) :
                usernameUserDetailsService.loadUserByUsername(consumedToken.getUsername());
        val userIdentifier = UserIdentifierModel.of(userDetails);
        return new MovieEzFullyAuthenticatedUser(userIdentifier, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OneTimeTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean isStringEmail(String data) {
        val matches = data.matches(emailRegex);
        return matches;
    }
}
