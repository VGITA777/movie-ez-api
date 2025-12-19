package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Password;
import com.prince.movieezapi.user.validators.annotations.Required;

public record PasswordInput(@Required(fieldName = "password") @Password String password) {

}
