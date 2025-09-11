package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.shared.utilities.UserSecurityUtils;
import com.prince.movieezapi.user.inputs.UpdatePasswordInput;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user/account/security")
public class UserAccountSecurityController {
    private final MovieEzUserService movieEzUserService;

    public UserAccountSecurityController(MovieEzUserService movieEzUserService) {
        this.movieEzUserService = movieEzUserService;
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePasswordByEmail(@RequestBody UpdatePasswordInput input, Authentication principal) {
        String email = principal.getName();
        validateUpdatePasswordInput(input, email);
        movieEzUserService.updatePasswordByEmail(email, input.oldPassword(), input.newPassword());
        return ResponseEntity.ok(ServerGenericResponse.success("Password updated successfully", null));
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
