package com.prince.movieezapi.security.authprovider;

import lombok.val;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class MovieEzOneTimeTokenAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OneTimeTokenAuthenticationToken oneTimeTokenAuthenticationToken = (OneTimeTokenAuthenticationToken) authentication;
        val credentials = oneTimeTokenAuthenticationToken.getCredentials();
        val tokenValue = oneTimeTokenAuthenticationToken.getTokenValue();
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OneTimeTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
