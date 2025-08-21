package com.prince.movieezapi.user.services;

import com.prince.movieezapi.security.services.JwtGeneratorService;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.responses.ServerAuthenticationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class UserAuthenticationService {
    private final JwtGeneratorService jwtGeneratorService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final MovieEzUserService movieEzUserService;

    public UserAuthenticationService(JwtGeneratorService jwtGeneratorService, PasswordEncoder bCryptPasswordEncoder, MovieEzUserService movieEzUserService) {
        this.jwtGeneratorService = jwtGeneratorService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.movieEzUserService = movieEzUserService;
    }

    public ResponseEntity<?> authenticateUserWithEmail(String email, String password) {
        MovieEzUserModel movieEzUserModel = movieEzUserService.findByEmail(email).orElseThrow(() -> {
            log.debug("User with email: {} was not found", email);
            return new UserNotFoundException("User not found with email: " + email);
        });
        if (!bCryptPasswordEncoder.matches(password, movieEzUserModel.getPassword())) {
            log.debug("Authentication failed for user with email: {}", email);
            throw new BadCredentialsException("Invalid password for user with email: " + email);
        }
        log.debug("User authenticated successfully with email: {}", email);
        return handleAuthentication(email);
    }

    public ResponseEntity<?> handleAuthentication(String id) {
        UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(id, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

        // Set Authentication to Security Context
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticated);
        SecurityContextHolder.setContext(context);

        // Generate JWT Token
        String token = jwtGeneratorService.genToken(authenticated);
        ServerAuthenticationResponse msg = new ServerAuthenticationResponse("Authentication Result", token, true);
        return ResponseEntity.ok(msg);
    }
}
