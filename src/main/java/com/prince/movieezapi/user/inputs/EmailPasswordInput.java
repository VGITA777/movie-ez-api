package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;
import jakarta.validation.constraints.Email;

public record EmailPasswordInput(@Required(fieldName = "email") @Email String email,
                                 @Required(fieldName = "password") @Password String password) {
}
