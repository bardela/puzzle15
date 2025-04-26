package com.ardela.puzzle15.service.board;

import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Cell;
import com.ardela.puzzle15.model.request.GameMove;
import com.ardela.puzzle15.utils.ArrayUtils;
import org.springframework.stereotype.Service;

import static com.ardela.puzzle15.utils.GameConstants.DIMENSION;
import static com.ardela.puzzle15.utils.GameConstants.EMPTY_PIECE;

@Service
public class BoardMoveService {

  public Cell findEmptyPiece(int[][] matrix) {
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        if (matrix[row][col] == EMPTY_PIECE) {
          return new Cell(row, col);
        }
      }
    }
    throw new IllegalStateException("Game Corrupted");
  }

  public boolean isGameSolved(Board board) {
    Integer previousNotEmptyPiece = null;
    for (int row = 0; row < DIMENSION; row++) {
      for (int col = 0; col < DIMENSION; col++) {
        int current = board.getPieces()[row][col];
        if (previousNotEmptyPiece != null && current > previousNotEmptyPiece){
          return false;
        }
        previousNotEmptyPiece = current != EMPTY_PIECE ? current : previousNotEmptyPiece;
      }
    }
    return true;
  }

  public Board swapPieces(Board board, Cell currentPosition, Cell newPosition) {
    int[][] matrix = ArrayUtils.copyMatrix(board.getPieces());
    int pieceToSwap = matrix[newPosition.getX()][newPosition.getY()];
    matrix[newPosition.getX()][newPosition.getY()] = matrix[currentPosition.getX()][currentPosition.getY()];
    matrix[currentPosition.getX()][currentPosition.getY()] = pieceToSwap;

    return new Board(matrix);
  }

  public Cell calculateNewCellPositionForMove(Cell original, GameMove move) {
    int x = original.getX();
    int y = original.getY();
    switch (move) {
      case UP -> x--;
      case DOWN -> x++;
      case LEFT -> y--;
      case RIGHT -> y++;
    }
    return new Cell(x, y);
  }
}
