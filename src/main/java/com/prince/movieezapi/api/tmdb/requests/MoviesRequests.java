package com.prince.movieezapi.api.tmdb.requests;

import com.prince.movieezapi.api.models.movies.*;
import com.prince.movieezapi.api.models.shared.CreditsModel;
import com.prince.movieezapi.api.models.shared.ImagesModel;
import com.prince.movieezapi.api.models.shared.VideosModel;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "https://api.themoviedb.org/3/movie")
public interface MoviesRequests {

    @GetExchange("/{movieId}/alternative_titles")
    MovieAlternativeTitlesModel getMovieAlternativeTitles(@PathVariable("movieId") long movieId, @RequestParam("country") String language);

    @GetExchange("/{movieId}/credits")
    CreditsModel getMovieCredits(@PathVariable("movieId") long movieId, @RequestParam("language") String language);

    @GetExchange("/{movieId}")
    MovieDetailsModel getMovieDetails(@PathVariable("movieId") long movieId, @RequestParam("language") String language);

    @GetExchange("/{movieId}/images")
    ImagesModel getMovieImages(@PathVariable("movieId") long movieId, @RequestParam("language") String language);

    @GetExchange("/{movieId}/keywords")
    MovieKeywordsModel getMovieKeywords(@PathVariable("movieId") long movieId);

    @GetExchange("/latest")
    MovieLatestModel getLatest();

    @GetExchange("/{movie_id}/recommendations")
    MovieRecommendationsModel getMovieRecommendations(@PathVariable("movie_id") long movieId, @RequestParam("language") String language, @RequestParam("page") int page);

    @GetExchange("/{movieId}/similar")
    MovieSimilarModel getMovieSimilar(@PathVariable("movieId") long movieId, @RequestParam("language") String language, @RequestParam("page") int page);

    @GetExchange("/{movieId}/videos")
    VideosModel getMovieVideos(@PathVariable("movieId") long movieId, @RequestParam("language") String language);
}