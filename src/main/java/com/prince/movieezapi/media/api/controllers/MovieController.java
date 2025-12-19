package com.prince.movieezapi.media.api.controllers;

import com.prince.movieezapi.media.api.models.enums.Country;
import com.prince.movieezapi.media.api.models.enums.Language;
import com.prince.movieezapi.media.api.tmdb.services.MovieRequestsService;
import com.prince.movieezapi.media.api.utils.ResponseEntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/media/v1/movie") public class MovieController {

    private final MovieRequestsService movieRequestsService;

    @Autowired
    public MovieController(MovieRequestsService movieRequestsService) {
        this.movieRequestsService = movieRequestsService;
    }

    @GetMapping("/{movieId}/alternative-titles")
    public ResponseEntity<?> getMovieAlternativeTitles(
            @PathVariable long movieId,
            @RequestParam(required = false) Country country
    ) {
        return ResponseEntityUtils.okPrivateOneWeek()
                                  .body(movieRequestsService.getMovieAlternativeTitles(movieId,
                                                                                       (country == null) ?
                                                                                       "" :
                                                                                       country.getIsoCode()));
    }

    @GetMapping("/{movieId}/credits")
    public ResponseEntity<?> getMovieCredits(
            @PathVariable long movieId,
            @RequestParam(defaultValue = "en", required = false) Language language
    ) {
        return ResponseEntityUtils.okPrivateOneWeek()
                                  .body(movieRequestsService.getMovieCredits(movieId, language.getIsoCode()));
    }

    @GetMapping("/{movieId}/details")
    public ResponseEntity<?> getMovieDetails(
            @PathVariable long movieId,
            @RequestParam(defaultValue = "en", required = false) Language language
    ) {
        return ResponseEntityUtils.okPrivateOneWeek()
                                  .body(movieRequestsService.getMovieDetails(movieId, language.getIsoCode()));
    }

    @GetMapping("/{movieId}/images")
    public ResponseEntity<?> getMovieImages(
            @PathVariable long movieId,
            @RequestParam(required = false) Language language
    ) {
        return ResponseEntityUtils.okPrivateOneWeek()
                                  .body(movieRequestsService.getMovieImages(movieId,
                                                                            (language == null) ?
                                                                            "" :
                                                                            language.getIsoCode()));
    }

    @GetMapping("/{movieId}/keywords")
    public ResponseEntity<?> getMovieKeywords(@PathVariable long movieId) {
        return ResponseEntityUtils.okPrivateOneWeek().body(movieRequestsService.getMovieKeywords(movieId));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatest() {
        return ResponseEntity.ok(movieRequestsService.getLatest());
    }

    @GetMapping("/{movieId}/recommendations")
    public ResponseEntity<?> getMovieRecommendations(
            @PathVariable long movieId,
            @RequestParam(defaultValue = "en", required = false) Language language,
            @RequestParam(defaultValue = "1", required = false) int page
    ) {
        return ResponseEntityUtils.okPrivateOneDay()
                                  .body(movieRequestsService.getMovieRecommendations(movieId,
                                                                                     language.getIsoCode(),
                                                                                     page));
    }

    @GetMapping("/{movieId}/similar")
    public ResponseEntity<?> getMovieSimilar(
            @PathVariable long movieId,
            @RequestParam(defaultValue = "en", required = false) Language language,
            @RequestParam(defaultValue = "1", required = false) int page
    ) {
        return ResponseEntityUtils.okPrivateOneDay()
                                  .body(movieRequestsService.getMovieSimilar(movieId, language.getIsoCode(), page));
    }

    @GetMapping("/{movieId}/videos")
    public ResponseEntity<?> getMovieVideos(
            @PathVariable long movieId,
            @RequestParam(required = false) Language language
    ) {
        return ResponseEntityUtils.okPrivateOneDay()
                                  .body(movieRequestsService.getMovieVideos(movieId,
                                                                            (language == null) ?
                                                                            "" :
                                                                            language.getIsoCode()));
    }
}
