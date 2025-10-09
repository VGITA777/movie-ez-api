package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.security.services.UserSessionService;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.shared.utilities.UserSecurityUtils;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserSessionMapper;
import com.prince.movieezapi.user.inputs.UpdatePasswordInput;
import com.prince.movieezapi.user.services.MovieEzUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController()
@RequestMapping("/user/account/security")
public class UserAccountSecurityController {
    private final MovieEzUserService movieEzUserService;
    private final UserSessionService userSessionService;

    public UserAccountSecurityController(MovieEzUserService movieEzUserService, UserSessionService userSessionService) {
        this.movieEzUserService = movieEzUserService;
        this.userSessionService = userSessionService;
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePasswordByEmail(@RequestBody UpdatePasswordInput input, Authentication principal, HttpSession session) {
        String email = principal.getName();
        validateUpdatePasswordInput(input, email);
        movieEzUserService.updatePasswordByEmail(email, input.oldPassword(), input.newPassword(), session, input.invalidateSessions());
        return ResponseEntity.ok(ServerGenericResponse.success("Password updated successfully", null));
    }

    @GetMapping("/sessions")
    public ResponseEntity<?> getAllSessions(Authentication authentication) {
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions", userSessionService
                .getSessionsByUser(authentication.getName())
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        (e) -> e.getValue().getId(),
                        (e) -> MovieEzUserSessionMapper.toDto(e.getValue())
                ))
        ));
    }

    @GetMapping("/sessions/current")
    public ResponseEntity<?> getCurrentSession(HttpSession session) {
        return ResponseEntity.ok(ServerGenericResponse.success("Current session", MovieEzUserSessionMapper.toDto(session)));
    }

    @DeleteMapping("/sessions/invalidate/{id}")
    public ResponseEntity<?> invalidateSession(@PathVariable String id, Authentication authentication) {
        userSessionService.deleteSessionById(id, authentication.getName());
        return ResponseEntity.ok(ServerGenericResponse.success("Session invalidated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/all")
    public ResponseEntity<?> invalidateAllSessions(Authentication authentication) {
        userSessionService.deleteAllSessionsByUsername(authentication.getName());
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions invalidated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/all/exclude-current")
    public ResponseEntity<?> invalidateAllSessionsExcludeCurrent(HttpSession session, Authentication authentication) {
        userSessionService.deleteAllSessionsByUsernameExcludeSessionId(authentication.getName(), session.getId());
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions invalidated successfully except current", null));
    }

    private void validateUpdatePasswordInput(UpdatePasswordInput input, String email) {
        if (!UserSecurityUtils.isPasswordValid(input.newPassword())) {
            throw new IllegalArgumentException("New password is not valid");
        }
        if (!UserSecurityUtils.isPasswordValid(input.oldPassword())) {
            throw new IllegalArgumentException("Old password is not valid");
        }
        if (!UserSecurityUtils.isEmailValid(email)) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if (input.newPassword().equals(input.oldPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
    }
}
