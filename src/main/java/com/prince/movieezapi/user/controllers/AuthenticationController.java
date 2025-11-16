package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.inputs.EmailPasswordInput;
import com.prince.movieezapi.user.inputs.UsernamePasswordInput;
import com.prince.movieezapi.user.services.UserEmailAndUsernameAuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/user/auth")
public class AuthenticationController {

    private final UserEmailAndUsernameAuthenticationService userEmailAndUsernameAuthenticationService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;
    private static final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthenticationController(UserEmailAndUsernameAuthenticationService userEmailAndUsernameAuthenticationService, SecurityContextLogoutHandler securityContextLogoutHandler) {
        this.userEmailAndUsernameAuthenticationService = userEmailAndUsernameAuthenticationService;
        this.securityContextLogoutHandler = securityContextLogoutHandler;
    }

    @GetMapping("/csrf")
    public ResponseEntity<?> getCsrfToken() {
        return ResponseEntity.ok(ServerAuthenticationResponse.success("CSRF token fetched", null));
    }

    @PostMapping("/login/email")
    public ResponseEntity<?> emailPasswordLogin(@RequestBody @Valid EmailPasswordInput input, HttpServletRequest request, HttpServletResponse response) {
        userEmailAndUsernameAuthenticationService.authenticateUserWithEmail(input.email(), input.password(), request, response);
        return ResponseEntity.ok(ServerAuthenticationResponse.success("Successfully authenticated", null));
    }

    @PostMapping("/login/username")
    public ResponseEntity<?> usernamePasswordLogin(@RequestBody @Valid UsernamePasswordInput input, HttpServletRequest request, HttpServletResponse response) {
        userEmailAndUsernameAuthenticationService.authenticateUserWithUsername(input.username(), input.password(), request, response);
        return ResponseEntity.ok(ServerAuthenticationResponse.success("Successfully authenticated", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();
        securityContextLogoutHandler.logout(request, response, authentication);
        deleteCookies(request);
        return ResponseEntity.ok(ServerGenericResponse.success("Successful log out", null));
    }

    private void deleteCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Arrays.stream(cookies).forEach(cookie -> {
            if (cookie.getName().contains("SESSION") || cookie.getName().contains("XSRF")) {
                cookie.setMaxAge(0);
                cookie.setValue("");
                cookie.setPath("/");
            }
        });
    }
}
