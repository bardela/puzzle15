package com.ardela.puzzle15.service;

import com.ardela.puzzle15.exception.UserNotFoundException;
import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) {
    return userRepository.findByUserName(username)
        .orElseThrow(() -> new UserNotFoundException(username));
  }
}
