package com.ardela.puzzle15.security.authentication;

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

  private static final String API_KEY_HEADER = "X-API-Key";
  private static final String VALID_API_KEY = "my-secret-api-key";


  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    String apiKey = request.getHeader(API_KEY_HEADER);
    if (!VALID_API_KEY.equals(apiKey)) {
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
        new UsernamePasswordAuthenticationToken(API_KEY_HEADER, null, null)
    );
  }
}