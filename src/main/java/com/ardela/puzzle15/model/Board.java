package com.ardela.puzzle15.model;

import lombok.Data;

import java.util.Arrays;

@Data
public class Board {
  private int[][] pieces;

  public Board() {
  }

  @Override
  public boolean equals(Object b) {
    if (b == null || b.getClass() != this.getClass()) {
      return false;
    }
    return Arrays.deepEquals(this.pieces, ((Board) b).getPieces());
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(this.pieces);
  }

  @Override
  public String toString() {
    return Arrays.deepToString(this.pieces);
  }
}
