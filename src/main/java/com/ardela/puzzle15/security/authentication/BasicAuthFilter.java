package com.ardela.puzzle15.security.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class BasicAuthFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    if (requiresBasicAuth(request) && !hasBasicAuth(request)) {
        throw new BadCredentialsException("Basic Auth required");
    }
    filterChain.doFilter(request, response);
  }

  boolean requiresBasicAuth(HttpServletRequest request) {
    return request.getRequestURI().startsWith("/employee");
  }

  boolean hasBasicAuth(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION) != null;
  }
}
