package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;

public record CloseAccountInput(@Required(message = "password field is required") @Password String password) {
}
