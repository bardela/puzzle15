package com.ardela.puzzle15.service.validation;

import com.ardela.puzzle15.exception.IllegalMoveOutOfBounds;
import com.ardela.puzzle15.exception.IllegalUpdateGameOver;
import com.ardela.puzzle15.exception.UnauthorizedUserInTheGameException;
import com.ardela.puzzle15.exception.UnauthorizedUserRolException;
import com.ardela.puzzle15.model.Cell;
import com.ardela.puzzle15.model.Game;
import com.ardela.puzzle15.model.Status;
import com.ardela.puzzle15.model.User;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import static com.ardela.puzzle15.utils.GameConstants.DIMENSION;

@Service
public class GameValidator {
  public void validateCreateGame(UserDetails userDetails) {
    if (userDetails == null || userDetails.getUsername() == null) {
      throw new InsufficientAuthenticationException("Not Authorize to create a Game");
    }
  }

  public void validateMovePiece(User user, Game game) {
    if (!game.getUserId().equals(user.getId())) {
      throw new UnauthorizedUserInTheGameException(user.getId(), game.getId());
    }
    if (!game.getStatus().equals(Status.PROGRESS)) {
      throw new IllegalUpdateGameOver(game.getId());
    }
  }

  public void validateBoardBounds(Cell newPosition) {
    if (newPosition.getX() < 0 || newPosition.getX() >= DIMENSION) {
      throw new IllegalMoveOutOfBounds("Vertical");
    }
    if (newPosition.getY() < 0 || newPosition.getY() >= DIMENSION) {
      throw new IllegalMoveOutOfBounds("Horizontal move out of Bounds");
    }
  }

  public void validateTerminateGame(UserDetails userDetails) {
    if (!hasRole(userDetails, "ADMIN")) {
      throw new UnauthorizedUserRolException(userDetails.getUsername());
    }
  }

  private boolean hasRole(UserDetails userDetails, String role) {
    return userDetails.getAuthorities().stream()
        .anyMatch(auth -> auth.getAuthority().equals(role));
  }
}
