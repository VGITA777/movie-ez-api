package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Username;
import jakarta.validation.constraints.Email;

public record UserRegistrationInput(@Username String username, @Email String email, @Password String password) {
}
