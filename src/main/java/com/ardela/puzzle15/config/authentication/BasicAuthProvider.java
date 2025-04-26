package com.ardela.puzzle15.config.authentication;

import com.ardela.puzzle15.service.AuthenticatedUserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class BasicAuthProvider implements AuthenticationProvider {

  private final AuthenticatedUserService authenticatedUserService;

  public BasicAuthProvider(AuthenticatedUserService authenticatedUserService) {
    this.authenticatedUserService = authenticatedUserService;
  }

  @Override
  public Authentication authenticate(Authentication auth) {
    String password = auth.getCredentials().toString();
    String username = auth.getName();

    UserDetails userDetails = authenticatedUserService.findByUsername(username);

    if (!authenticatedUserService.passwordsMatch(password, userDetails.getPassword())) {
      throw new BadCredentialsException("Invalid password");
    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
