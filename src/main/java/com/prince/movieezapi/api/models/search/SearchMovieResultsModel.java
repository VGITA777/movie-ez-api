package com.prince.movieezapi.api.models.search;

import com.prince.movieezapi.api.models.movies.MovieShortDetailsWithMediaTypeModel;
import com.prince.movieezapi.api.models.shared.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMovieResultsModel extends Page<MovieShortDetailsWithMediaTypeModel> {
}
