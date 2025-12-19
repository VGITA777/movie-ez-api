package com.prince.movieezapi.security.authenticationtokens;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * Base class marker for unauthenticated tokens.
 */
public abstract class UnauthenticatedToken extends AbstractAuthenticationToken {

  /**
   * Creates a token with the supplied array of authorities.
   *
   * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal represented by this
   *                    authentication object.
   */
  public UnauthenticatedToken(Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    throw new UnsupportedOperationException("Cannot change authentication state");
  }

  @Override
  public boolean isAuthenticated() {
    return false;
  }
}
