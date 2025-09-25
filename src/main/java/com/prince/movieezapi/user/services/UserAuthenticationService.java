package com.prince.movieezapi.user.services;

import com.prince.movieezapi.security.tokens.MovieEzEmailPasswordAuthenticationToken;
import com.prince.movieezapi.security.tokens.MovieEzUsernamePasswordAuthenticationToken;
import com.prince.movieezapi.shared.utilities.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final HttpSessionSecurityContextRepository securityContextRepository;

    public UserAuthenticationService(AuthenticationManager authenticationManager, HttpSessionSecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    public void authenticateUserWithEmail(String email, String password, HttpServletRequest request, HttpServletResponse response) {
        log.info("Authenticating user with email: {}", email);
        MovieEzEmailPasswordAuthenticationToken unauthenticated = MovieEzEmailPasswordAuthenticationToken.unauthenticated(email, password);
        Authentication authenticated = authenticationManager.authenticate(unauthenticated);
        SecurityUtils.setCurrentAuthentication(authenticated, securityContextRepository, request, response);
        log.info("Authentication successful for user with email: {}", email);
    }

    public void authenticateUserWithUsername(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        log.info("Authenticating user with username: {}", username);
        MovieEzUsernamePasswordAuthenticationToken unauthenticated = MovieEzUsernamePasswordAuthenticationToken.unauthenticated(username, password);
        Authentication authenticated = authenticationManager.authenticate(unauthenticated);
        SecurityUtils.setCurrentAuthentication(authenticated, securityContextRepository, request, response);
        log.info("Authentication successful for user with username: {}", username);
    }
}
