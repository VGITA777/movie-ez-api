package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;

public record UpdatePasswordInput(@Required(fieldName = "oldPassword") @Password(message = "Old password is invalid") String oldPassword,
                                  @Required(fieldName = "newPassword") @Password(message = "New password is invalid") String newPassword,
                                  @Required(fieldName = "invalidateSessions") boolean invalidateSessions,
                                  boolean invalidateAllSessions) {

}
