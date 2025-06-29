package com.prince.movieezapi.api.http.requests;

import com.prince.movieezapi.api.models.movies.MovieAlternativeTitlesResponse;
import com.prince.movieezapi.api.models.movies.MovieCreditsResponse;
import com.prince.movieezapi.api.models.movies.MovieDetailsResponse;
import com.prince.movieezapi.api.models.movies.MovieImagesResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://api.themoviedb.org/3/movie/{movieId}")
public interface MoviesRequests {

    @GetExchange("/alternative_titles")
    MovieAlternativeTitlesResponse getMovieAlternativeTitles(@PathVariable("movieId") long movieId, @RequestParam("country") String language);

    @GetExchange("/credits")
    MovieCreditsResponse getMovieCredits(@PathVariable("movieId") long movieId, @RequestParam("language") String language);

    @GetExchange
    MovieDetailsResponse getMovieDetails(@PathVariable("movieId") long movieId, @RequestParam("language") String language);

    @GetExchange("/images")
    MovieImagesResponse getMovieImages(@PathVariable("movieId") long movieId, @RequestParam("language") String language);

    @GetExchange("/keywords")
    MovieAlternativeTitlesResponse getMovieKeywords(@PathVariable("movieId") long movieId);
}