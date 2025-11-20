package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.security.services.UserSessionService;
import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.dto.MovieEzUserDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserDtoMapper;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserSessionDtoMapper;
import com.prince.movieezapi.user.dto.mappers.SpringSessionMapper;
import com.prince.movieezapi.user.inputs.PasswordInput;
import com.prince.movieezapi.user.inputs.UpdatePasswordInput;
import com.prince.movieezapi.user.inputs.UpdateUsernameInput;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.services.MovieEzUserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RateLimiter(name = "userAccountSecurityEndpoints")
@RestController()
@RequestMapping("/user/account/security")
public class UserAccountSecurityController {
    private final MovieEzUserService movieEzUserService;
    private final UserSessionService userSessionService;
    private final MovieEzUserDtoMapper movieEzUserDtoMapper;
    private final MovieEzUserSessionDtoMapper movieEzUserSessionDtoMapper;
    private final SpringSessionMapper springSessionMapper;

    public UserAccountSecurityController(MovieEzUserService movieEzUserService, UserSessionService userSessionService, MovieEzUserDtoMapper movieEzUserDtoMapper, MovieEzUserSessionDtoMapper movieEzUserSessionDtoMapper, SpringSessionMapper springSessionMapper) {
        this.movieEzUserService = movieEzUserService;
        this.userSessionService = userSessionService;
        this.movieEzUserDtoMapper = movieEzUserDtoMapper;
        this.movieEzUserSessionDtoMapper = movieEzUserSessionDtoMapper;
        this.springSessionMapper = springSessionMapper;
    }

    @GetMapping("/client")
    public ResponseEntity<?> getCurrentClient(@AuthenticationPrincipal UUID uuid) {
        MovieEzUserModel user = movieEzUserService.findById(uuid).orElseThrow(() -> new RuntimeException("User not found with ID: '" + uuid + "'"));
        MovieEzUserDto mapped = movieEzUserDtoMapper.toDto(user);
        return ResponseEntity.ok().body(ServerGenericResponse.success("User Details", mapped));
    }

    @GetMapping("/sessions")
    public ResponseEntity<?> getAllSessions(@AuthenticationPrincipal UUID uuid) {
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions", userSessionService
                .getSessionsByPrincipalName(uuid)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        (e) -> e.getValue().getId(),
                        (e) -> movieEzUserSessionDtoMapper.toDto(e.getValue())
                ))
        ));
    }

    @GetMapping("/sessions/current")
    public ResponseEntity<?> getCurrentSession(HttpSession session) {
        return ResponseEntity.ok(ServerGenericResponse.success("Current session", springSessionMapper.toDto(session)));
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid UpdatePasswordInput input, @AuthenticationPrincipal UUID uuid, HttpSession session) {
        validateUpdatePasswordInput(input);
        movieEzUserService.updatePasswordById(uuid, input.oldPassword(), input.newPassword(), session, input.invalidateSessions(), input.invalidateAllSessions());
        return ResponseEntity.ok(ServerGenericResponse.success("Password updated successfully", null));
    }

    @PatchMapping("/update-username")
    public ResponseEntity<?> updateUsername(@RequestBody @Valid UpdateUsernameInput input, @AuthenticationPrincipal UUID uuid) {
        movieEzUserService.updateUsernameById(uuid, input.username());
        return ResponseEntity.ok(ServerGenericResponse.success("Username updated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/{id}")
    public ResponseEntity<?> invalidateSession(@PathVariable String id, @AuthenticationPrincipal UUID uuid) {
        userSessionService.deleteSessionById(id, uuid);
        return ResponseEntity.ok(ServerGenericResponse.success("Session invalidated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/all")
    public ResponseEntity<?> invalidateAllSessions(@AuthenticationPrincipal UUID uuid) {
        userSessionService.deleteAllSessionsByPrincipalName(uuid);
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions invalidated successfully", null));
    }

    @DeleteMapping("/sessions/invalidate/all/exclude-current")
    public ResponseEntity<?> invalidateAllSessionsExcludeCurrent(HttpSession session, @AuthenticationPrincipal UUID uuid) {
        userSessionService.deleteAllSessionsByPrincipalNameExcludeSessionId(uuid, session.getId());
        return ResponseEntity.ok(ServerGenericResponse.success("All sessions invalidated successfully except current", null));
    }

    @DeleteMapping("/close-account")
    public ResponseEntity<?> closeAccount(@RequestBody PasswordInput passwordInput, @AuthenticationPrincipal UUID uuid) {
        movieEzUserService.delete(uuid, passwordInput.password());
        return ResponseEntity.ok(ServerGenericResponse.success("Account closed successfully", null));
    }

    private void validateUpdatePasswordInput(UpdatePasswordInput input) {
        if (input.newPassword().equals(input.oldPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
    }
}
