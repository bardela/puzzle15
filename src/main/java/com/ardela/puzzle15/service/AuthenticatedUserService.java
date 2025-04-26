package com.ardela.puzzle15.service;

import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedUserService {

  private final UserRepository userRepository;
  public AuthenticatedUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserDetails findByUsername(String username) {
    User user = userRepository.findByUserName(username)
        .orElseThrow(() -> new UsernameNotFoundException("Authenticated user not found"));

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
