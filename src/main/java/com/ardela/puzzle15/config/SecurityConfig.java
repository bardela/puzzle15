package com.ardela.puzzle15.config;

import com.ardela.puzzle15.config.authentication.BasicAuthFilter;
import com.ardela.puzzle15.config.authentication.ApiKeyAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain filterChainBasicAuth(
      HttpSecurity http,
      BasicAuthFilter basicAuthFilter,
      ApiKeyAuthFilter apiKeyAuthFilter
  ) throws Exception {
    http
        .securityMatcher("/games/")
        .httpBasic(Customizer.withDefaults())
        .addFilterBefore(basicAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return buildFilterChain(http, apiKeyAuthFilter);
  }

  @Bean
  @Order(2)
  public SecurityFilterChain filterChainOnlyApiKey(
      HttpSecurity http,
      ApiKeyAuthFilter apiKeyAuthFilter
  ) throws Exception {
    return buildFilterChain(http, apiKeyAuthFilter);
  }

  SecurityFilterChain buildFilterChain(HttpSecurity http, ApiKeyAuthFilter apiKeyAuthFilter) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
    ;
    return http.build();
  }
}