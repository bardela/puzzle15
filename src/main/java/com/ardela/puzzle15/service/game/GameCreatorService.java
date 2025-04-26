package com.ardela.puzzle15.service.game;

import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.model.Status;
import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.repository.GameRepository;
import com.ardela.puzzle15.service.UserService;
import com.ardela.puzzle15.service.board.BoardCreatorService;
import com.ardela.puzzle15.service.validation.GameValidator;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class GameCreatorService {
  private final BoardCreatorService boardCreatorService;
  private final GameValidator gameValidator;
  private final UserService userService;
  private final GameRepository gameRepository;

  public GameCreatorService(
      BoardCreatorService boardCreatorService,
      GameValidator gameValidator,
      UserService userService,
      GameRepository gameRepository
  ) {
    this.boardCreatorService = boardCreatorService;
    this.gameValidator = gameValidator;
    this.userService = userService;
    this.gameRepository = gameRepository;
  }

  public Game createNewGame(UserDetails userDetails) {
    gameValidator.validateCreateGame(userDetails);
    User user = userService.findByUsername(userDetails.getUsername());

    return gameRepository.save(
        prepareCreateGame(user.getId())
    );
  }

  Game prepareCreateGame(Integer userId) {
    Board board = new Board(
        boardCreatorService.generateBoardPieces()
    );

    return new Game(
        userId,
        board,
        Status.PROGRESS
    );
  }
}
