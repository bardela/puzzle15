package com.ardela.puzzle15.service;

import com.ardela.puzzle15.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

  private final UserService userService;
  public AuthenticatedUserService(UserService userService) {
    this.userService = userService;
  }

  public UserDetails findByUsername(String username) {
    User user = userService.findByUsername(username);

    return org.springframework.security.core.userdetails.User
        .withUsername(user.getUsername())
        .password(user.getPasswordHash())
        .roles(user.getRol())
        .build();
  }

  public boolean passwordsMatch(String password, String encodedPassword) {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder.matches(password, encodedPassword);
  }
}
