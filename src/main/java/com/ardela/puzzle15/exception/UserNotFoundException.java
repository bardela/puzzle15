package com.ardela.puzzle15.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(String user) {
    super("User " + user + " not found.");
  }
}
