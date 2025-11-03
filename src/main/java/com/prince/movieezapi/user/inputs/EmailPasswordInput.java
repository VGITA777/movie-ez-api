package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import jakarta.validation.constraints.Email;

public record EmailPasswordInput(@Email String email, @Password String password) {
}
