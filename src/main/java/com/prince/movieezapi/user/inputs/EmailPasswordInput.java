package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;
import jakarta.validation.constraints.Email;

public record EmailPasswordInput(@Required(message = "'email' field is required") @Email String email,
                                 @Required(message = "'password' field is required") @Password String password) {
}
