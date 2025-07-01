package com.prince.movieezapi.api.controllers;

import com.prince.movieezapi.api.models.enums.Country;
import com.prince.movieezapi.api.models.enums.Language;
import com.prince.movieezapi.api.tmdb.services.MovieRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {

    private final MovieRequestsService movieRequestsService;

    @Autowired
    public MovieController(MovieRequestsService movieRequestsService) {
        this.movieRequestsService = movieRequestsService;
    }

    @GetMapping("/{movieId}/alternative-titles")
    public ResponseEntity<?> getMovieAlternativeTitles(@PathVariable long movieId, @RequestParam(required = false) Country country) {
        return ResponseEntity.ok(movieRequestsService.getMovieAlternativeTitles(movieId, (country == null) ? "" : country.getIsoCode()));
    }

    @GetMapping("/{movieId}/credits")
    public ResponseEntity<?> getMovieCredits(@PathVariable long movieId, @RequestParam(defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(movieRequestsService.getMovieCredits(movieId, language.getIsoCode()));
    }

    @GetMapping("/{movieId}/details")
    public ResponseEntity<?> getMovieDetails(@PathVariable long movieId, @RequestParam(defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(movieRequestsService.getMovieDetails(movieId, language.getIsoCode()));
    }

    @GetMapping("/{movieId}/images")
    public ResponseEntity<?> getMovieImages(@PathVariable long movieId, @RequestParam(required = false) Language language) {
        return ResponseEntity.ok(movieRequestsService.getMovieImages(movieId, (language == null) ? "" : language.getIsoCode()));
    }

    @GetMapping("/{movieId}/keywords")
    public ResponseEntity<?> getMovieKeywords(@PathVariable long movieId) {
        return ResponseEntity.ok(movieRequestsService.getMovieKeywords(movieId));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        return ResponseEntity.ok(movieRequestsService.getLatest());
    }

    @GetMapping("/{movieId}/recommendations")
    public ResponseEntity<?> getMovieRecommendations(@PathVariable long movieId,
                                                     @RequestParam(defaultValue = "en", required = false) Language language,
                                                     @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(movieRequestsService.getMovieRecommendations(movieId, language.getIsoCode(), page));
    }

    @GetMapping("/{movieId}/similar")
    public ResponseEntity<?> getMovieSimilar(@PathVariable long movieId,
                                             @RequestParam(defaultValue = "en", required = false) Language language,
                                             @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(movieRequestsService.getMovieSimilar(movieId, language.getIsoCode(), page));
    }

    @GetMapping("/{movieId}/videos")
    public ResponseEntity<?> getMovieVideos(@PathVariable long movieId, @RequestParam(required = false) Language language) {
        return ResponseEntity.ok(movieRequestsService.getMovieVideos(movieId, (language == null) ? "" : language.getIsoCode()));
    }
}
