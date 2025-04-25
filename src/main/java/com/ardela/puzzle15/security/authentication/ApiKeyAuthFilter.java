package com.ardela.puzzle15.security.authentication;

import com.ardela.puzzle15.properties.PropertiesConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

  private final String authClientHeader;

  private final String authClientKey;

  public ApiKeyAuthFilter(PropertiesConfig propertiesConfig) {
    authClientHeader = propertiesConfig.getAuthClientHeader();
    authClientKey = propertiesConfig.getAuthClientKey();
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    String requestApiKey = request.getHeader(authClientHeader);
    if (requestApiKey == null || !requestApiKey.equals(authClientKey)) {
      throw new BadCredentialsException("Client not valid Api Key");
    }

    setAuthentication(SecurityContextHolder.getContext());
    filterChain.doFilter(request, response);
  }

  private void setAuthentication(SecurityContext context) {
    if (context.getAuthentication() != null) {
      return;
    }

    context.setAuthentication(
        new UsernamePasswordAuthenticationToken(authClientHeader, null, null)
    );
  }
}