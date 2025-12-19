package com.prince.movieezapi.security.authenticationtokens;

import com.prince.movieezapi.shared.models.UserIdentifierModel;
import com.prince.movieezapi.user.models.MovieEzAppRole;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * A fully authenticated user token for the MovieEz application. This token should be used carefully because it is a
 * marker globally used to identify an authenticated user.
 */
public final class MovieEzFullyAuthenticatedUser extends AbstractAuthenticationToken implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private final UserIdentifierModel userIdentifierModel;

  public MovieEzFullyAuthenticatedUser(
      UserIdentifierModel userIdentifierModel,
      Collection<? extends GrantedAuthority> authorities
  ) {
    super(authorities);
    this.userIdentifierModel = userIdentifierModel;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public UUID getPrincipal() {
    return userIdentifierModel.id();
  }

  @Override
  public String getName() {
    return userIdentifierModel.username();
  }

  /**
   * Will always return true.
   */
  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public UserIdentifierModel getDetails() {
    return userIdentifierModel;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    throw new UnsupportedOperationException("Cannot change authentication state");
  }

  @Override
  public void setDetails(Object details) {
    throw new UnsupportedOperationException("Cannot change authentication state");
  }

  public MovieEzAppRole getHighestPriorityRole() {
    return getAuthorities()
        .stream()
        .map(grantedAuthority -> MovieEzAppRole.valueOf(grantedAuthority.getAuthority()))
        .max(Comparator.comparingInt(MovieEzAppRole::getPriority))
        .orElse(MovieEzAppRole.GUEST);
  }
}
