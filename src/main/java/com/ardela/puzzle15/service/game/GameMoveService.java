package com.ardela.puzzle15.service.game;

import com.ardela.puzzle15.exception.GameNotFoundException;
import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Cell;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.model.Status;
import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.model.request.GameMove;
import com.ardela.puzzle15.repository.GameRepository;
import com.ardela.puzzle15.service.board.BoardMoveService;
import com.ardela.puzzle15.service.UserService;
import com.ardela.puzzle15.service.validation.GameValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameMoveService {

  private final UserService userService;
  private final GameRepository gameRepository;
  private final GameValidator gameValidator;
  private final BoardMoveService boardMoveService;


  public GameMoveService(
      GameRepository gameRepository,
      UserService userService,
      GameValidator gameValidator,
      BoardMoveService boardMoveService
  ) {
    this.gameRepository = gameRepository;
    this.userService = userService;
    this.gameValidator = gameValidator;
    this.boardMoveService = boardMoveService;
  }

  public Game movePiece(Integer gameId, GameMove move, UserDetails userDetails) {
    Game game = gameRepository.findById(gameId)
        .orElseThrow(() -> new GameNotFoundException(gameId));
    User user = userService.findByUsername(userDetails.getUsername());

    gameValidator.validateMovePiece(user, game);

    return updateBoardGame(game, move);
  }

  Game updateBoardGame(Game game, GameMove move) {
    Cell currentPosition = boardMoveService.findEmptyPiece(game.getBoard().getPieces());
    Cell newPosition = boardMoveService.calculateNewCellPositionForMove(currentPosition, move);
    gameValidator.validateBoardBounds(newPosition);

    return gameRepository.save(
        prepareGameToUpdate(game, currentPosition, newPosition)
    );
  }

  Game prepareGameToUpdate(Game game, Cell currentPosition, Cell newPosition) {
    Board board = boardMoveService.swapPieces(game.getBoard(), currentPosition, newPosition);
    Status status = boardMoveService.isGameSolved(board) ? Status.FINISHED : game.getStatus();

    return new Game(
        game.getId(),
        game.getUserId(),
        board,
        status,
        new Date()
    );
  }
}
