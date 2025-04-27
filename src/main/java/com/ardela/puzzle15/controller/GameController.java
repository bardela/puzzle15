package com.ardela.puzzle15.controller;

import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.model.request.GameMoveRequest;
import com.ardela.puzzle15.service.game.GameCreatorService;
import com.ardela.puzzle15.service.game.GameMoveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  private final GameCreatorService gameCreatorService;
  private final GameMoveService gameMoveService;

  public GameController(
      GameCreatorService gameCreatorService,
      GameMoveService gameMoveService
  ) {
    this.gameCreatorService = gameCreatorService;
    this.gameMoveService = gameMoveService;
  }

  @PostMapping("/")
  public ResponseEntity<Game> create(@AuthenticationPrincipal UserDetails userDetails) {
    return new ResponseEntity<>(
        gameCreatorService.createNewGame(userDetails),
        HttpStatus.OK
    );
  }

  @PostMapping("/{gameId}/move")
  public ResponseEntity<Game> move(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody GameMoveRequest moveRequest,
      @PathVariable Integer gameId
  ) {
    return new ResponseEntity<>(
        gameMoveService.movePiece(gameId, moveRequest.getMove(), userDetails),
        HttpStatus.OK
    );
  }

  @PostMapping("/{gameId}/terminate")
  public ResponseEntity<Game> terminate(
      @AuthenticationPrincipal UserDetails userDetails,
      @PathVariable Integer gameId
  ) {
    // TODO: terminate
    return new ResponseEntity<>(
        new Game(),
        HttpStatus.OK
    );
  }
}
