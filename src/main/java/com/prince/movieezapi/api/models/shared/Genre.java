package com.prince.movieezapi.api.models.shared;

import lombok.Data;

/**
 * Represents a genre for movies and TV series.
 * This is a shared model used by both movie and TV series responses.
 */
@Data
public class Genre {
    private long id;
    private String name;
}