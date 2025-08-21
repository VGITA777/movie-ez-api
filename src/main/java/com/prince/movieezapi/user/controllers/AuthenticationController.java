package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.inputs.EmailPasswordInput;
import com.prince.movieezapi.user.services.UserAuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/user/auth")
public class AuthenticationController {

    private final UserAuthenticationService userAuthenticationService;

    public AuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> emailPasswordLogin(@RequestBody EmailPasswordInput input) throws IOException {
        return userAuthenticationService.authenticateUserWithEmail(input.email(), input.password());
    }
}
