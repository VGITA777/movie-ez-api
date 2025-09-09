package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.UserSecurityUtils;
import com.prince.movieezapi.user.exceptions.MalformedEmailException;
import com.prince.movieezapi.user.exceptions.MalformedPasswordException;
import com.prince.movieezapi.user.inputs.EmailPasswordInput;
import com.prince.movieezapi.user.services.UserAuthenticationService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/auth")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    public AuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @SneakyThrows
    public static void validateEmailPasswordInput(EmailPasswordInput input) {
        if (!UserSecurityUtils.isEmailValid(input.email())) {
            throw new MalformedEmailException("Malformed email");
        }
        if (!UserSecurityUtils.isPasswordValid(input.password())) {
            throw new MalformedPasswordException("Malformed password");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> emailPasswordLogin(@RequestBody EmailPasswordInput input) {
        validateEmailPasswordInput(input);
        return userAuthenticationService.authenticateUserWithEmail(input.email(), input.password());
    }
}
