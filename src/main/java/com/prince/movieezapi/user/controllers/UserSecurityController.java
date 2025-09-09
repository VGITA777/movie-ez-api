package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.UserSecurityUtils;
import com.prince.movieezapi.user.inputs.UpdatePasswordInput;
import com.prince.movieezapi.user.responses.ServerGenericResponse;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user/security")
public class UserSecurityController {
    private final MovieEzUserService movieEzUserService;

    public UserSecurityController(MovieEzUserService movieEzUserService) {
        this.movieEzUserService = movieEzUserService;
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePasswordByEmail(@RequestBody UpdatePasswordInput input, @AuthenticationPrincipal Jwt principal) {
        String email = principal.getSubject();
        validateUpdatePasswordInput(input, email);
        movieEzUserService.updatePasswordByEmail(email, input.oldPassword(), input.newPassword());
        return ResponseEntity.ok(new ServerGenericResponse("Password updated successfully", null, true));
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
