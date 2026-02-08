package com.prince.movieezapi.security.keycloak;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.keycloak.admin")
public record KeycloakAdminConfigurationProperties(@NotNull
                                                   String serverUrl,
                                                   @NotNull
                                                   String realm,
                                                   @NotNull
                                                   String secret) {

  public KeycloakAdminConfigurationProperties {
    assert serverUrl != null : "Keycloak server URL cannot be null";
    assert realm != null : "Keycloak realm cannot be null";
    assert secret != null : "Keycloak secret cannot be null";
  }
}
