package com.ardela.puzzle15.repository;

import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameRepositoryTests {

  GameRepository gameRepository;

  @BeforeEach
  void setUp() {
    gameRepository = new GameRepository();
  }

  Game generateTestGame(Integer userId) {
    int[][] pieces = {
        {-1, 5},
        {12, 9},
    };
    Board board = new Board(pieces);

    Instant instant1secondAgo = Instant.now().minusSeconds(1);
    Date date1secondAgo = Date.from(instant1secondAgo);
    return new Game(
        null, userId, board, Status.PROGRESS, date1secondAgo
    );
  }

  @Test
  void save_newGame() {
    // Arrange
    int numberOfGamesBefore = gameRepository.count();
    Game gameToSave = generateTestGame(3);

    // Act
    Game savedGame = gameRepository.save(gameToSave);

    // Assert
    assertEquals(1, gameRepository.count());
    assertEquals(0, numberOfGamesBefore);
    assertNotNull(savedGame.getId());
    assertEquals(3, savedGame.getUserId());
    assertEquals(gameToSave.getBoard(), savedGame.getBoard());
    assertEquals(gameToSave.getStatus(), savedGame.getStatus());
    assertEquals(gameToSave.getUpdatedAt(), savedGame.getUpdatedAt());
  }

  @Test
  void save_existingGame() {
    // Arrange
    Game initialGame = gameRepository.save(generateTestGame(1));
    int countBefore = gameRepository.count();

    Board boardToUpdate = new Board(new int[][]{{14}});
    Game gameToUpdate = new Game(
        initialGame.getId(),
        4,
        boardToUpdate,
        Status.TERMINATED,
        new Date()
    );

    // Act
    Game updatedGame = gameRepository.save(gameToUpdate);

    // Assert
    assertEquals(gameToUpdate.getId(), updatedGame.getId());
    assertEquals(gameToUpdate.getUserId(), updatedGame.getUserId());
    assertEquals(gameToUpdate.getStatus(), updatedGame.getStatus());
    assertEquals(gameToUpdate.getBoard(), updatedGame.getBoard());
    assertEquals(gameToUpdate.getUpdatedAt(), updatedGame.getUpdatedAt());

    assertNotEquals(initialGame.getUserId(), updatedGame.getUserId());
    assertNotEquals(initialGame.getStatus(), updatedGame.getStatus());
    assertNotEquals(initialGame.getBoard(), updatedGame.getBoard());
    assertNotEquals(initialGame.getUpdatedAt(), updatedGame.getUpdatedAt());
    assertEquals(gameRepository.count(), countBefore);
  }

  @Test
  void count_beforeAndAfterInserts() {
    // Arrange
    Game inputGame2 = new Game();

    // Act
    int countBeforeInserts = gameRepository.count();
    gameRepository.save(generateTestGame(1));
    int countAfter1Insert = gameRepository.count();
    gameRepository.save(inputGame2);
    int countAfter2Inserts = gameRepository.count();

    // Assert
    assertTrue(countBeforeInserts < countAfter1Insert);
    assertTrue(countAfter1Insert < countAfter2Inserts);
  }

  @Test
  void findById_whenIdIsNull(){
    // Arrange
    gameRepository.save(generateTestGame(1));
    // Act
    Optional<Game> game = gameRepository.findById(null);
    // Assert
    assertTrue(game.isEmpty());
  }

  @Test
  void findById_whenIdDoesntExist(){
    // Arrange
    gameRepository.save(generateTestGame(1));
    // Act
    Optional<Game> game = gameRepository.findById(2);
    // Assert
    assertTrue(game.isEmpty());
  }

  @Test
  void findById_success(){
    // Arrange
    Game generatedGame = gameRepository.save(generateTestGame(1));
    Game savedAnotherGame = gameRepository.save(new Game());
    // Act
    Optional<Game> foundGeneratedGame = gameRepository.findById(generatedGame.getId());
    // Assert
    assertTrue(foundGeneratedGame.isPresent());
    assertEquals(generatedGame.getId(), foundGeneratedGame.get().getId());
    assertNotEquals(savedAnotherGame.getId(), foundGeneratedGame.get().getId());
  }

  @Test
  void findAll(){
    // Arrange
    Game game2 = new Game();
    // Act
    List<Game> gamesBeforeInserts = gameRepository.findAll();
    gameRepository.save(generateTestGame(1));
    List<Game> gamesAfter1Insert = gameRepository.findAll();
    gameRepository.save(game2);
    List<Game> gamesAfter2Inserts = gameRepository.findAll();
    // Assert
    assertTrue(gamesBeforeInserts.size() < gamesAfter1Insert.size());
    assertTrue(gamesAfter1Insert.size() < gamesAfter2Inserts.size());
  }
}
