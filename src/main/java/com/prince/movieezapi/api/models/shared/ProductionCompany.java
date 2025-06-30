package com.prince.movieezapi.api.models.shared;

import lombok.Data;

/**
 * Represents a production company for movies and TV series.
 * This is a shared model used by both movie and TV series responses.
 */
@Data
public class ProductionCompany {
    private long id;
    private String logo_path;
    private String name;
    private String origin_country;
}