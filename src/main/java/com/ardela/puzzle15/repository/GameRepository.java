package com.ardela.puzzle15.repository;

import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.repository.base.InMemoryRepository;
import com.ardela.puzzle15.utils.ArrayUtils;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepository extends InMemoryRepository<Game> {
  @Override
  public Game copyData(Game origin) {
    return new Game(
        origin.getId(),
        origin.getUserId(),
        copyBoard(origin.getBoard()),
        origin.getStatus(),
        origin.getUpdatedAt()
    );
  }

  Board copyBoard(Board origin) {
    return new Board(
        ArrayUtils.copyMatrix(origin.getPieces())
    );
  }
}
