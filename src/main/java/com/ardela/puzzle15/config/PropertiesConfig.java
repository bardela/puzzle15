package com.ardela.puzzle15.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class PropertiesConfig {
  @Value("${auth.client.header}")
  private String authClientHeader;

  @Value("${auth.client.key}")
  private String authClientKey;

  public String getAuthClientHeader() {
    return authClientHeader;
  }
  public String getAuthClientKey() {
    return authClientKey;
  }
}
