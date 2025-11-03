package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;

public record UpdatePasswordInput(
        @Required(message = "'oldPassword' field is required") @Password(message = "Old password is invalid") String oldPassword,
        @Required(message = "'newPassword' field is required") @Password(message = "New password is invalid") String newPassword,
        @Required(message = "'invalidateSessions' field is required") boolean invalidateSessions,
        boolean invalidateAllSessions) {
}
