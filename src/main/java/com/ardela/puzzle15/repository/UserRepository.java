package com.ardela.puzzle15.repository;

import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.repository.base.InMemoryRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository extends InMemoryRepository<User> {

  @Override
  protected User copyData(User origin) {
    User user = new User();
    user.setId(origin.getId());
    user.setUsername(origin.getUsername());
    user.setPasswordHash(origin.getPasswordHash());
    user.setRol(origin.getRol());
    return user;
  }

  public Optional<User> findByUserName(String username) {
    return collection.values().stream()
        .filter(user -> user.getUsername().equals(username))
        .findFirst();
  }

}
