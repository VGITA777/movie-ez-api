package com.prince.movieezapi.api.controllers;

import com.prince.movieezapi.api.models.enums.Country;
import com.prince.movieezapi.api.models.enums.Language;
import com.prince.movieezapi.api.tmdb.requests.MoviesRequests;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {

    private final MoviesRequests moviesRequests;

    public MovieController(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.moviesRequests = httpServiceProxyFactory.createClient(MoviesRequests.class);
    }

    @GetMapping("/{movieId}/alternative-titles")
    public ResponseEntity<?> getMovieAlternativeTitles(@PathVariable long movieId, @RequestParam(required = false) Country country) {
        return ResponseEntity.ok(moviesRequests.getMovieAlternativeTitles(movieId, (country == null) ? "" : country.getIsoCode()));
    }

    @GetMapping("/{movieId}/credits")
    public ResponseEntity<?> getMovieCredits(@PathVariable long movieId, @RequestParam(defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(moviesRequests.getMovieCredits(movieId, language.getIsoCode()));
    }

    @GetMapping("/{movieId}/details")
    public ResponseEntity<?> getMovieDetails(@PathVariable long movieId, @RequestParam(defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(moviesRequests.getMovieDetails(movieId, language.getIsoCode()));
    }

    @GetMapping("/{movieId}/images")
    public ResponseEntity<?> getMovieImages(@PathVariable long movieId, @RequestParam(required = false) Language language) {
        return ResponseEntity.ok(moviesRequests.getMovieImages(movieId, (language == null) ? "" : language.getIsoCode()));
    }

    @GetMapping("/{movieId}/keywords")
    public ResponseEntity<?> getMovieKeywords(@PathVariable long movieId) {
        return ResponseEntity.ok(moviesRequests.getMovieKeywords(movieId));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        return ResponseEntity.ok(moviesRequests.getLatest());
    }

    @GetMapping("/{movieId}/recommendations")
    public ResponseEntity<?> getMovieRecommendations(@PathVariable long movieId,
                                                     @RequestParam(defaultValue = "en", required = false) Language language,
                                                     @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(moviesRequests.getMovieRecommendations(movieId, language.getIsoCode(), page));
    }

    @GetMapping("/{movieId}/similar")
    public ResponseEntity<?> getMovieSimilar(@PathVariable long movieId,
                                             @RequestParam(defaultValue = "en", required = false) Language language,
                                             @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(moviesRequests.getMovieSimilar(movieId, language.getIsoCode(), page));
    }

    @GetMapping("/{movieId}/videos")
    public ResponseEntity<?> getMovieVideos(@PathVariable long movieId, @RequestParam(required = false) Language language) {
        return ResponseEntity.ok(moviesRequests.getMovieVideos(movieId, (language == null) ? "" : language.getIsoCode()));
    }
}
