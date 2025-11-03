package com.prince.movieezapi.user.exceptions;

public class PlaylistAlreadyExistsException extends NotFoundException {
    public PlaylistAlreadyExistsException(String message) {
        super(message);
    }
}
