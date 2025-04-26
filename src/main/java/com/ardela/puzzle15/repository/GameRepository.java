package com.ardela.puzzle15.repository;

import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.repository.base.InMemoryRepository;
import com.ardela.puzzle15.utils.ArrayUtils;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository extends InMemoryRepository<Game> {
  @Override
  protected Game copyData(Game origin) {
    Game game = new Game();
    game.setId(origin.getId());
    game.setUserId(origin.getUserId());
    game.setStatus(origin.getStatus());
    game.setUpdatedAt(origin.getUpdatedAt());

    Board board = copyBoard(origin.getBoard());
    game.setBoard(board);

    return game;
  }

  Board copyBoard(Board origin) {
    Board board = new Board();
    board.setPieces(ArrayUtils.copyMatrix(origin.getPieces()));
    return board;
  }
}
