package com.ardela.puzzle15.model;

import com.ardela.puzzle15.model.base.EntityWithId;
import lombok.Data;

@Data
public class User implements EntityWithId {
  private Integer id;
  private String username;
  private String passwordHash;
  private String rol;

  public User() {
  }
}
