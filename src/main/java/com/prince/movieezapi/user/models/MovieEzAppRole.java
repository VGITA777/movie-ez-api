package com.prince.movieezapi.user.models;

import lombok.Getter;

/**
 * An enum of available roles.
 */
@Getter
public enum MovieEzAppRole {
    GUEST(0),
    USER(1),
    ADMIN(Integer.MAX_VALUE);

    private final int priority;

    MovieEzAppRole(int priority) {
        this.priority = priority;
    }
}
