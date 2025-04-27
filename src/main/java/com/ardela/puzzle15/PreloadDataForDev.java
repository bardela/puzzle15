package com.ardela.puzzle15;

import com.ardela.puzzle15.model.User;
import com.ardela.puzzle15.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class PreloadDataForDev {

  @Value("${preload.user.username}")
  private String regularUsername;
  @Value("${preload.user.password}")
  private String regularPassword;

  @Value("${preload.admin.username}")
  private String adminUsername;
  @Value("${preload.admin.password}")
  private String adminPassword;

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

  public PreloadDataForDev(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    User regularUser = createUser(regularUsername, regularPassword, "REGULAR");
    userRepository.save(regularUser);

    User adminUser = createUser(adminUsername, adminPassword, "ADMIN");
    userRepository.save(adminUser);
  }

  private User createUser(String username, String password, String role) {
    User user = new User();
    user.setUsername(username);
    user.setPasswordHash(bCryptPasswordEncoder.encode(password));
    user.setRol(role);
    return user;
  }
}