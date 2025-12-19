package com.prince.movieezapi.user.exceptions;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
