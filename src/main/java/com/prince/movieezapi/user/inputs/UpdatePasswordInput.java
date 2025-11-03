package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;

public record UpdatePasswordInput(@Password(message = "Old password is invalid") String oldPassword,
                                  @Password(message = "New password is invalid") String newPassword,
                                  @Required(message = "invalidateSessions field is required") boolean invalidateSessions,
                                  boolean invalidateAllSessions) {
}
