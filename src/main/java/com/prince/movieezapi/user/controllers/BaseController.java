package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.exceptions.MalformedEmailException;
import com.prince.movieezapi.user.exceptions.MalformedPasswordException;
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

    /*
     *   Authentication Exceptions Handling
     * */

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServerAuthenticationResponse("Authentication Failed", "Invalid Credentials", false));
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<?> handleCredentialException(CredentialException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ServerAuthenticationResponse("Authentication Failed", e.getMessage(), false));
    }

    /*
     *   Controller Inputs Exceptions Handling
     * */

    @ExceptionHandler(MalformedEmailException.class)
    public ResponseEntity<?> handleMalformedEmailException(MalformedEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServerAuthenticationResponse("Invalid Email", e.getMessage(), false));
    }

    @ExceptionHandler(MalformedPasswordException.class)
    public ResponseEntity<?> handleMalformedPasswordException(MalformedPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServerAuthenticationResponse("Invalid Password", e.getMessage(), false));
    }

    /*
     *   User Related Exceptions Handling
     * */

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ServerAuthenticationResponse("User Not Found", e.getMessage(), false));
    }
}
