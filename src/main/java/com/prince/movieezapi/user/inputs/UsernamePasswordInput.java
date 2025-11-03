package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;
import com.prince.movieezapi.user.validators.annotations.Username;

public record UsernamePasswordInput(@Required(fieldName = "username") @Username String username,
                                    @Required(fieldName = "password") @Password String password) {
}
