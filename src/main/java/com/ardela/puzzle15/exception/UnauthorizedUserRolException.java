package com.ardela.puzzle15.exception;

public class UnauthorizedUserRolException extends RuntimeException {
  public UnauthorizedUserRolException(String user) {
    super("User " + user + " is not authorized with its role");
  }
}
