package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.exceptions.MalformedEmailException;
import com.prince.movieezapi.user.exceptions.MalformedPasswordException;
import com.prince.movieezapi.user.exceptions.NotFoundException;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.CredentialException;
import java.sql.SQLException;
import java.util.List;

@ControllerAdvice
public class UserControllerAdvice {

    /*
     *   Authentication Exceptions Handling
     * */

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerAuthenticationResponse.failure("User Not Found", e.getMessage()));
    }

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

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException e) {
        String message = e.getMessage();
        if (message.contains("Duplicate entry")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ServerAuthenticationResponse.failure("Conflict", "User already exists"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServerAuthenticationResponse.failure("Database Error", e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerGenericResponse.failure("Playlist Error", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .filter(message -> message != null && !message.isBlank())
                .distinct()
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerGenericResponse.failure("Invalid Input", errors));
    }
}
