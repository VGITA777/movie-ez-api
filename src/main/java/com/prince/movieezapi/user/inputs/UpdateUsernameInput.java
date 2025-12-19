package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Required;
import com.prince.movieezapi.user.validators.annotations.Username;

public record UpdateUsernameInput(@Required(fieldName = "username") @Username String username) {

}
