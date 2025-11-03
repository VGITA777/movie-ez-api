package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Username;

public record UsernamePasswordInput(@Username String username, @Password String password) {
}
