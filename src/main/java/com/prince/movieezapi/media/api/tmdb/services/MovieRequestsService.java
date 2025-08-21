package com.prince.movieezapi.media.api.tmdb.services;

import com.prince.movieezapi.media.api.models.movies.*;
import com.prince.movieezapi.media.api.models.shared.CreditsModel;
import com.prince.movieezapi.media.api.models.shared.ImagesModel;
import com.prince.movieezapi.media.api.models.shared.VideosModel;
import com.prince.movieezapi.media.api.tmdb.requests.MoviesRequests;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class MovieRequestsService {
    private final MoviesRequests moviesRequests;

    public MovieRequestsService(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.moviesRequests = httpServiceProxyFactory.createClient(MoviesRequests.class);
    }

    @Cacheable(cacheNames = "movieAlternativeTitles")
    public MovieAlternativeTitlesModel getMovieAlternativeTitles(long movieId, String country) {
        return moviesRequests.getMovieAlternativeTitles(movieId, country);
    }

    @Cacheable(cacheNames = "movieCredits")
    public CreditsModel getMovieCredits(long movieId, String language) {
        return moviesRequests.getMovieCredits(movieId, language);
    }

    @Cacheable(cacheNames = "movieDetails")
    public MovieDetailsModel getMovieDetails(long movieId, String language) {
        return moviesRequests.getMovieDetails(movieId, language);
    }

    @Cacheable(cacheNames = "movieImages")
    public ImagesModel getMovieImages(long movieId, String language) {
        return moviesRequests.getMovieImages(movieId, language);
    }

    @Cacheable(cacheNames = "movieKeywords")
    public MovieKeywordsModel getMovieKeywords(long movieId) {
        return moviesRequests.getMovieKeywords(movieId);
    }

    public MovieLatestModel getLatest() {
        return moviesRequests.getLatest();
    }

    @Cacheable(cacheNames = "movieRecommendations")
    public MovieRecommendationsModel getMovieRecommendations(long movieId, String language, int page) {
        return moviesRequests.getMovieRecommendations(movieId, language, page);
    }

    @Cacheable(cacheNames = "movieSimilar")
    public MovieSimilarModel getMovieSimilar(long movieId, String language, int page) {
        return moviesRequests.getMovieSimilar(movieId, language, page);
    }

    @Cacheable(cacheNames = "movieVideos")
    public VideosModel getMovieVideos(long movieId, String language) {
        return moviesRequests.getMovieVideos(movieId, language);
    }
}
