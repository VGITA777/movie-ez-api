package com.prince.movieezapi.user.exceptions;

public class PlaylistAlreadyExistsException extends ResourceAlreadyExistsException {

  public PlaylistAlreadyExistsException(String message) {
    super(message);
  }
}
