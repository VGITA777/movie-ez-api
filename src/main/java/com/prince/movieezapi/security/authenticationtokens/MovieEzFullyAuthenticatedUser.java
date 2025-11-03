package com.prince.movieezapi.security.authenticationtokens;

import com.prince.movieezapi.shared.models.UserIdentifierModel;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

/**
 * A fully authenticated user token for the MovieEz application.
 * This token represents a user that has been fully authenticated
 * and contains their granted authorities.
 */
public class MovieEzFullyAuthenticatedUser extends AbstractAuthenticationToken implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final UserIdentifierModel userIdentifierModel;

    public MovieEzFullyAuthenticatedUser(UserIdentifierModel userIdentifierModel, Collection<? extends GrantedAuthority> authorities) {
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
}
