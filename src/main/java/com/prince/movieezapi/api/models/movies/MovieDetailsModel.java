package com.prince.movieezapi.api.models.movies;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prince.movieezapi.api.json.deserializers.StringToMediaTypeDeserializer;
import com.prince.movieezapi.api.json.serailizers.MediaTypeToStringSerializer;
import com.prince.movieezapi.api.models.enums.MediaType;
import com.prince.movieezapi.api.models.shared.MediaDetailsModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response model for The Movie Database (TMDB) API's movie details endpoint.
 * Contains detailed information about a specific movie including its basic info, production details, and statistics.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-details">Movie Details API Reference</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MovieDetailsModel extends MediaDetailsModel {
    private BelongsToCollection belongs_to_collection;
    private long budget;
    private String imdb_id;
    private String original_title;
    private String release_date;
    private long revenue;
    private int runtime;
    private String title;
    private boolean video;
    @JsonDeserialize(using = StringToMediaTypeDeserializer.class)
    @JsonSerialize(using = MediaTypeToStringSerializer.class)
    private MediaType media_type = MediaType.MOVIE;

    @Data
    public static class BelongsToCollection {
        private long id;
        private String name;
        private String poster_path;
        private String backdrop_path;
    }

}
