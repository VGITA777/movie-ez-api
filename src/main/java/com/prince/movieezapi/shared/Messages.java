package com.prince.movieezapi.shared;

import com.prince.movieezapi.user.responses.ServerAuthenticationResponse;

public class Messages {
    public static final String SUCCESS_AUTH_MSG = "Successfully authenticated";
    public static final ServerAuthenticationResponse SUCCESS_AUTH_RESPONSE = new ServerAuthenticationResponse(SUCCESS_AUTH_MSG, null, true);
}
