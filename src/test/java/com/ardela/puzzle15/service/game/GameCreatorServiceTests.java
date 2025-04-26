package com.ardela.puzzle15.service.game;

import com.ardela.puzzle15.model.Board;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.model.Status;
import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.repository.GameRepository;
import com.ardela.puzzle15.service.UserService;
import com.ardela.puzzle15.service.board.BoardCreatorService;
import com.ardela.puzzle15.service.validation.GameValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.ardela.puzzle15.utils.GameConstants.DIMENSION;
import static com.ardela.puzzle15.utils.GameConstants.MAX_PIECE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameCreatorServiceTests {
  @Mock
  UserService userService;

  @Mock
  UserDetails userDetails;


  BoardCreatorService boardCreatorService;

  GameValidator gameValidator;

  GameRepository gameRepository;


  private GameCreatorService gameCreatorService;

  @BeforeEach
  void setUp() {
    this.boardCreatorService = new BoardCreatorService();
    this.gameValidator = new GameValidator();
    this.gameRepository = new GameRepository();

    this.gameCreatorService = new GameCreatorService(
        boardCreatorService,
        gameValidator,
        userService,
        gameRepository
    );
  }

  @Test
  void createNewGame_fails_validation() {
    // Arrange
    when(userDetails.getUsername()).thenReturn(null);

    // Assert
    assertThrows(InsufficientAuthenticationException.class, () ->
      gameCreatorService.createNewGame(userDetails)
    );
  }

  User createUser(String username) {
    User user = new User();
    user.setId(1);
    user.setUsername(username);
    user.setRol("USER");
    return user;
  }

  @Test
  void createNewGame_success() {
    // Arrange
    when(userDetails.getUsername()).thenReturn("john");
    User user = createUser("john");
    when(userService.findByUsername(userDetails.getUsername())).thenReturn(user);

    // Act
    Game newGame = gameCreatorService.createNewGame(userDetails);

    // Assert
    assertNotNull(newGame);
    assertNotNull(newGame.getId());
    assertEquals(user.getId(), newGame.getId());
    assertEquals(Status.PROGRESS, newGame.getStatus());
    assertNotNull(newGame.getUpdatedAt());
    assertBoardForNewGame(newGame.getBoard());
  }

  void assertBoardForNewGame(Board board) {
    // Arrange
    List<Integer> expectedPieces = IntStream.range(0, MAX_PIECE).boxed().toList();
    int[][] pieces = board.getPieces();
    List<Integer> listOfPieces = matrixToList(pieces);

    // Assert
    assertNotNull(board);
    assertNotNull(pieces);
    assertEquals(pieces.length, DIMENSION);
    assertEquals(listOfPieces.size(), DIMENSION * DIMENSION);
    assertTrue(listOfPieces.containsAll(expectedPieces));
  }

  List<Integer> matrixToList(int[][] matrix) {
    List<Integer> list = new ArrayList<>();
    for (int[] row : matrix) {
      for (int content : row) {
        list.add(content);
      }
    }
    return list;
  }
}
