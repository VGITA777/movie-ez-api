package com.prince.movieezapi.shared.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record UserIdentifierModel(UUID id, String username, String email) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
