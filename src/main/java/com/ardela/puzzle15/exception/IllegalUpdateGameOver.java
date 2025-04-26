package com.ardela.puzzle15.exception;

public class IllegalUpdateGameOver extends RuntimeException {
  public IllegalUpdateGameOver(Integer gameId) {
    super("The Game " + gameId + " is over ");
  }
}
