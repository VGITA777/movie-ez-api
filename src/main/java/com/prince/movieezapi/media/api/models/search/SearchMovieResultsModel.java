package com.prince.movieezapi.media.api.models.search;

import com.prince.movieezapi.media.api.models.movies.MovieShortDetailsWithMediaTypeModel;
import com.prince.movieezapi.media.api.models.shared.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMovieResultsModel extends Page<MovieShortDetailsWithMediaTypeModel> {

}
