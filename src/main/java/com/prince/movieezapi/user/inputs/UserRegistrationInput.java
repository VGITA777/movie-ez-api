package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;
import com.prince.movieezapi.user.validators.annotations.Username;
import jakarta.validation.constraints.Email;

public record UserRegistrationInput(@Required(fieldName = "username") @Username String username,
                                    @Required(fieldName = "email") @Email String email,
                                    @Required(fieldName = "password") @Password String password) {
}
