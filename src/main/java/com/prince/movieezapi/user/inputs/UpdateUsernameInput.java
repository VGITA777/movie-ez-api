package com.prince.movieezapi.user.inputs;

import com.prince.movieezapi.user.validators.annotations.Username;

public record UpdateUsernameInput(@Username String username) {
}
