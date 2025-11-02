package com.prince.movieezapi.shared.models;

import java.util.UUID;

public record UserIdentifierModel(UUID id, String username, String email) {
}
