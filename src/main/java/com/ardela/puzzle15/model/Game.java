package com.ardela.puzzle15.model;

import com.ardela.puzzle15.model.base.EntityWithId;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@Data
public class Game implements EntityWithId {
  private Integer id;
  private Integer userId;
  private Board board;

  private Status status;
  private Date updatedAt;

  public Game(Integer id, Integer userId, Board board, Status status, Date updatedAt) {
    this.id = id;
    this.userId = userId;
    this.board = board;
    this.status = status;
    this.updatedAt = updatedAt;
  }

  public Game(Integer userId, Board board, Status status) {
    this(
        null,
        userId,
        board,
        status,
        new Date()
    );
  }
}

