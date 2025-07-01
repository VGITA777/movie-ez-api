package com.prince.movieezapi.api.tmdb.services;

import com.prince.movieezapi.api.models.movies.*;
import com.prince.movieezapi.api.models.shared.CreditsModel;
import com.prince.movieezapi.api.models.shared.ImagesModel;
import com.prince.movieezapi.api.models.shared.VideosModel;
import com.prince.movieezapi.api.tmdb.requests.MoviesRequests;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class MovieRequestsService {
    private final MoviesRequests moviesRequests;

    public MovieRequestsService(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.moviesRequests = httpServiceProxyFactory.createClient(MoviesRequests.class);
    }

    public MovieAlternativeTitlesModel getMovieAlternativeTitles(long movieId, String country) {
        return moviesRequests.getMovieAlternativeTitles(movieId, country);
    }

    public CreditsModel getMovieCredits(long movieId, String language) {
        return moviesRequests.getMovieCredits(movieId, language);
    }

    public MovieDetailsModel getMovieDetails(long movieId, String language) {
        return moviesRequests.getMovieDetails(movieId, language);
    }

    public ImagesModel getMovieImages(long movieId, String language) {
        return moviesRequests.getMovieImages(movieId, language);
    }

    public MovieKeywordsModel getMovieKeywords(long movieId) {
        return moviesRequests.getMovieKeywords(movieId);
    }

    public MovieLatestModel getLatest() {
        return moviesRequests.getLatest();
    }

    public MovieRecommendationsModel getMovieRecommendations(long movieId, String language, int page) {
        return moviesRequests.getMovieRecommendations(movieId, language, page);
    }

    public MovieSimilarModel getMovieSimilar(long movieId, String language, int page) {
        return moviesRequests.getMovieSimilar(movieId, language, page);
    }

    public VideosModel getMovieVideos(long movieId, String language) {
        return moviesRequests.getMovieVideos(movieId, language);
    }
}
