package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.exceptions.UserNotFoundException;
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

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<?> handleCredentialException(CredentialException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServerAuthenticationResponse("Authentication Failed", e.getMessage(), false));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServerAuthenticationResponse("User Not Found", e.getMessage(), false));
    }
}
