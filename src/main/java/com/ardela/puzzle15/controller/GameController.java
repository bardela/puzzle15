package com.ardela.puzzle15.controller;

import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.service.game.GameCreatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  private final GameCreatorService gameCreatorService;

  public GameController(
      GameCreatorService gameCreatorService
  ) {
    this.gameCreatorService = gameCreatorService;
  }

  @PostMapping("/")
  public ResponseEntity<Game> create(@AuthenticationPrincipal UserDetails userDetails) {
    return new ResponseEntity<>(
        gameCreatorService.createNewGame(userDetails),
        HttpStatus.OK
    );
  }
}
