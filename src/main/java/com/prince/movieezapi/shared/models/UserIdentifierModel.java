package com.prince.movieezapi.shared.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * A model that should be used as the Principal for a fully authenticated user.
 * @param id the {@link UUID} of the user.
 * @param username the username of the user.
 * @param email the email of the user.
 */
public record UserIdentifierModel(UUID id, String username, String email) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
