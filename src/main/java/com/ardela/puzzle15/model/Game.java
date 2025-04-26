package com.ardela.puzzle15.model;

import com.ardela.puzzle15.model.base.EntityWithId;
import lombok.Data;

import java.util.Date;

@Data
public class Game implements EntityWithId {
  private Integer id;
  private Integer userId;
  private Board board;

  private Status status;
  private Date updatedAt;
}

