package com.prince.movieezapi.security.keycloak;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.keycloak.admin")
public class KeycloakAdminConfigurationProperties {

  private final String secret;
  private final String realm;
  private final String serverUrl;

  public KeycloakAdminConfigurationProperties(String serverUrl, String realm, String secret) {
    if (serverUrl == null || serverUrl.isBlank()) {
      throw new IllegalArgumentException("Property 'serverUrl' cannot be null or empty");
    }
    if (realm == null || realm.isBlank()) {
      throw new IllegalArgumentException("Property 'realm' cannot be null or empty");
    }
    if (secret == null || secret.isBlank()) {
      throw new IllegalArgumentException("Property 'secret' cannot be null or empty");
    }

    this.serverUrl = serverUrl;
    this.realm = realm;
    this.secret = secret;
  }
}