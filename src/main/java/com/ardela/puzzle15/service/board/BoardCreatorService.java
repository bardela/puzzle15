package com.ardela.puzzle15.service.board;

import com.ardela.puzzle15.utils.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static com.ardela.puzzle15.utils.GameConstants.DIMENSION;
import static com.ardela.puzzle15.utils.GameConstants.EMPTY_PIECE;
import static com.ardela.puzzle15.utils.GameConstants.MAX_PIECE;

@Service
public class BoardCreatorService {
  public int[][] generateBoardPieces() {
    List<Integer> randomizedPieces = ArrayUtils.generateSequenceInRandomOrder(EMPTY_PIECE, MAX_PIECE);
    return generateMatrixPieces(randomizedPieces);
  }

  int[][] generateMatrixPieces(List<Integer> pieces) {
    return IntStream.range(0, DIMENSION)
        .mapToObj(row -> generateRowPieces(pieces))
        .toArray(int[][]::new);
  }

  int[] generateRowPieces(List<Integer> pieces) {
    return IntStream.range(0, DIMENSION)
        .map(col -> popRandomPiece(pieces))
        .toArray();
  }
  int popRandomPiece(List<Integer> boardPieces) {
    Random random = new Random();
    int indexPiece = boardPieces.size() > 1 ? random.nextInt(boardPieces.size() - 1) : 0;
    return boardPieces.remove(indexPiece);
  }
}
