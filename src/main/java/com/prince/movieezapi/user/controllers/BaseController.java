package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.responses.ServerAuthenticationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.CredentialException;

@ControllerAdvice
public class BaseController {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(CredentialException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServerAuthenticationResponse("Authentication Failed", "Invalid Credentials", false));
    }

}
