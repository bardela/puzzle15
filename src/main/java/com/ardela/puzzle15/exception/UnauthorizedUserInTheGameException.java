package com.ardela.puzzle15.exception;

public class UnauthorizedUserInTheGameException extends RuntimeException {
  public UnauthorizedUserInTheGameException(Integer userId, Integer gameId) {
    super("User " + userId + " is not Authorized in the game " + gameId);
  }
}
