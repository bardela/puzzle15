package com.ardela.puzzle15.model;

import com.ardela.puzzle15.model.base.EntityWithId;

public class User implements EntityWithId {
  private Integer id;
  private String username;
  private String passwordHash;
  private String rol;

  public User() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getRol() {
    return rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }
}
