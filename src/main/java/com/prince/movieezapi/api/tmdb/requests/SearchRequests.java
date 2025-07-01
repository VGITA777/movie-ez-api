package com.prince.movieezapi.api.tmdb.requests;

import com.prince.movieezapi.api.models.search.SearchMovieResultsModel;
import com.prince.movieezapi.api.models.search.SearchMultiResultsModel;
import com.prince.movieezapi.api.models.search.SearchTvSeriesResultsModel;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://api.themoviedb.org/3/search")
public interface SearchRequests {

    @GetExchange("/movie")
    SearchMovieResultsModel searchMovies(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "include_adult", defaultValue = "false", required = false) boolean includeAdult,
            @RequestParam(value = "language", defaultValue = "en", required = false) String language,
            @RequestParam(value = "primary_release_year", required = false) Integer primaryReleaseYear,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "region", required = false) String region,
            @RequestParam(value = "year", required = false) Integer year
    );

    @GetExchange("/tv")
    SearchTvSeriesResultsModel searchTvSeries(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "first_air_date_year", required = false) Integer firstAirDateYear,
            @RequestParam(value = "include_adult", defaultValue = "false", required = false) boolean includeAdult,
            @RequestParam(value = "language", defaultValue = "en", required = false) String language,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "year", required = false) Integer year
    );

    @GetExchange("/multi")
    SearchMultiResultsModel searchMulti(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "include_adult", defaultValue = "false", required = false) boolean includeAdult,
            @RequestParam(value = "language", defaultValue = "en", required = false) String language,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page
    );
}
