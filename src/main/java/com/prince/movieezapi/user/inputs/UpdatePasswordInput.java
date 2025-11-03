package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;

public record UpdatePasswordInput(@Password String oldPassword, @Password String newPassword,
                                  boolean invalidateSessions) {
}
