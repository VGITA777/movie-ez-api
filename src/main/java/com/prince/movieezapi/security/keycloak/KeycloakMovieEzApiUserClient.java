package com.prince.movieezapi.security.keycloak;


import java.util.stream.Stream;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public class KeycloakMovieEzApiUserClient {

  public final Keycloak keycloak;
  private final RealmResource realmResource;
  private final UsersResource usersResource;

  public KeycloakMovieEzApiUserClient(Keycloak keycloak) {
    this.keycloak = keycloak;
    this.realmResource = keycloak.realm("movie-ez");
    this.usersResource = realmResource.users();
  }

  public int countUsers() {
    return usersResource.count();
  }

  public int countVerifiedUsers() {
    return usersResource.countEmailVerified(true);
  }

  public Stream<UserRepresentation> allUsers() {
    return usersResource
        .list()
        .stream();
  }

}
