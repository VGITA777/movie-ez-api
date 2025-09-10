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
public class UserControllerAdvice {

    /*
     *   Authentication Exceptions Handling
     * */

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ServerAuthenticationResponse.failure("Invalid Credentials", e.getMessage()));
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<?> handleCredentialException(CredentialException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ServerAuthenticationResponse.failure("Authentication Failed", e.getMessage()));
    }

    @ExceptionHandler(MalformedEmailException.class)
    public ResponseEntity<?> handleMalformedEmailException(MalformedEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerAuthenticationResponse.failure("Invalid Email", e.getMessage()));
    }

    @ExceptionHandler(MalformedPasswordException.class)
    public ResponseEntity<?> handleMalformedPasswordException(MalformedPasswordException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerAuthenticationResponse.failure("Invalid Password", e.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerAuthenticationResponse.failure("User Not Found", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerAuthenticationResponse.failure("Invalid Input", e.getMessage()));
    }
}
