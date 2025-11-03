package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;
import com.prince.movieezapi.user.validators.annotations.Username;

public record UsernamePasswordInput(@Required(message = "'username' field is required") @Username String username,
                                    @Required(message = "'password' field is required") @Password String password) {
}
