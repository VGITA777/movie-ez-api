package com.prince.movieezapi.user.responses;

public record ServerAuthenticationResponse(String message, Object details, boolean success) {
}
