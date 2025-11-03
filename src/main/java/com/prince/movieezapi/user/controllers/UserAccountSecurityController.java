package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.security.services.UserSessionService;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserSessionMapper;
import com.prince.movieezapi.user.inputs.CloseAccountInput;
import com.prince.movieezapi.user.inputs.UpdatePasswordInput;
import com.prince.movieezapi.user.inputs.UpdateUsernameInput;
import com.prince.movieezapi.user.services.MovieEzUserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
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
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordInput input, Authentication authentication, HttpSession session) {
        UUID uuid = (UUID) authentication.getPrincipal();
        validateUpdatePasswordInput(input);
        movieEzUserService.updatePasswordById(uuid, input.oldPassword(), input.newPassword(), session, input.invalidateSessions(), input.invalidateAllSessions());
        return ResponseEntity.ok(ServerGenericResponse.success("Password updated successfully", null));
    }

    @PatchMapping("/update-username")
    public ResponseEntity<?> updateUsername(@RequestBody @Valid UpdateUsernameInput input, Authentication authentication) {
        UUID uuid = (UUID) authentication.getPrincipal();
        movieEzUserService.updateUsernameById(uuid, input.username());
        return ResponseEntity.ok(ServerGenericResponse.success("Username updated successfully", null));
    }

    @GetMapping("/sessions")
    public ResponseEntity<?> getAllSessions(Authentication authentication) {
        UUID uuid = (UUID) authentication.getPrincipal();
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions", userSessionService
                .getSessionsByPrincipalName(uuid)
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
        UUID uuid = (UUID) authentication.getPrincipal();
        userSessionService.deleteSessionById(id, uuid);
        return ResponseEntity.ok(ServerGenericResponse.success("Session invalidated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/all")
    public ResponseEntity<?> invalidateAllSessions(Authentication authentication) {
        UUID uuid = (UUID) authentication.getPrincipal();
        userSessionService.deleteAllSessionsByPrincipalName(uuid);
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions invalidated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/all/exclude-current")
    public ResponseEntity<?> invalidateAllSessionsExcludeCurrent(HttpSession session, Authentication authentication) {
        UUID uuid = (UUID) authentication.getPrincipal();
        userSessionService.deleteAllSessionsByPrincipalNameExcludeSessionId(uuid, session.getId());
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions invalidated successfully except current", null));
    }

    @DeleteMapping("/close-account")
    public ResponseEntity<?> closeAccount(@RequestBody CloseAccountInput closeAccountInput, Authentication authentication) {
        UUID uuid = (UUID) authentication.getPrincipal();
        movieEzUserService.delete(uuid, closeAccountInput.password());
        return ResponseEntity.ok(ServerGenericResponse.success("Account closed successfully", null));
    }

    private void validateUpdatePasswordInput(UpdatePasswordInput input) {
        if (input.newPassword().equals(input.oldPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
    }
}
