package com.ardela.puzzle15.exception;

public class GameNotFoundException extends RuntimeException {
  public GameNotFoundException(Integer id) {
    super("Game " + id + " not found.");
  }
}
