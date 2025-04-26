package com.ardela.puzzle15.exception;

public class IllegalMoveOutOfBounds extends RuntimeException  {
  public IllegalMoveOutOfBounds(String axis) {
    super(axis + " move out of Bounds");
  }
}
