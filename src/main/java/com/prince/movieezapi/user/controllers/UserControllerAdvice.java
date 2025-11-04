package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerAuthenticationResponse;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.exceptions.MalformedEmailException;
import com.prince.movieezapi.user.exceptions.MalformedPasswordException;
import com.prince.movieezapi.user.exceptions.NotFoundException;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

    private final MessageSource messageSource;

    public UserControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException e) {
        String title = msg("auth.userNotFound.title", "User Not Found");
        String base = msg("auth.userNotFound.message", "No matching user account found.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e) {
        String title = msg("auth.badCredentials.title", "Invalid Credentials");
        String base = msg("auth.badCredentials.message", "The username or password is incorrect.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<?> handleCredentialException(CredentialException e) {
        String title = msg("auth.credentialFailure.title", "Authentication Failed");
        String base = msg("auth.credentialFailure.message", "Authentication could not be completed.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(MalformedEmailException.class)
    public ResponseEntity<?> handleMalformedEmailException(MalformedEmailException e) {
        String title = msg("auth.invalidEmail.title", "Invalid Email");
        String base = msg("auth.invalidEmail.message", "Provided email is not valid.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(MalformedPasswordException.class)
    public ResponseEntity<?> handleMalformedPasswordException(MalformedPasswordException e) {
        String title = msg("auth.invalidPassword.title", "Invalid Password");
        String base = msg("auth.invalidPassword.message", "Password does not meet the required format.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        String title = msg("auth.userNotFound.title", "User Not Found");
        String base = msg("auth.userNotFound.message", "No matching user account found.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        String title = msg("error.invalidInput.title", "Invalid Input");
        String base = msg("error.invalidInput.message", "Request contains invalid parameters.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerAuthenticationResponse.failure(title, message));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<?> handleSQLException(SQLException e) {
        String detail = e.getMessage();
        String message = detail != null ? detail : "Database error";
        if (message.contains("Duplicate entry")) {
            String title = msg("sql.duplicate.title", "Conflict");
            String base = msg("sql.duplicate.message", "An entry with the same key already exists.");
            String body = appendDetail(base, detail);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ServerAuthenticationResponse.failure(title, body));
        }
        String title = msg("sql.error.title", "Database Error");
        String base = msg("sql.error.message", "An unexpected database error occurred.");
        String body = appendDetail(base, detail);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ServerAuthenticationResponse.failure(title, body));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException e) {
        String title = msg("resource.notFound.title", "Resource Error");
        String base = msg("resource.notFound.message", "Requested resource not found.");
        String message = appendDetail(base, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerGenericResponse.failure(title, message));
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
        String title = msg("validation.invalid.title", "Invalid Input");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ServerGenericResponse.failure(title, errors));
    }

    private String msg(String key, String defaultMessage, Object... args) {
        return messageSource.getMessage(key, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    private String msg(String key, String defaultMessage) {
        return msg(key, defaultMessage, new Object[]{});
    }

    private String appendDetail(String base, String detail) {
        if (detail == null || detail.isBlank()) return base;
        String trimmed = detail.trim();
        // avoid duplicating detail if already included
        if (base.endsWith(trimmed) || base.contains("(" + trimmed + ")")) return base;
        return base + " (" + trimmed + ")";
    }

}
