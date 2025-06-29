package com.prince.movieezapi.api.controllers;

import com.prince.movieezapi.api.http.requests.MoviesRequests;
import com.prince.movieezapi.api.models.enums.Country;
import com.prince.movieezapi.api.models.enums.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RestController
@RequestMapping("/v1/movie/{movieId}")
public class MovieController {

    private final MoviesRequests moviesRequests;

    public MovieController(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.moviesRequests = httpServiceProxyFactory.createClient(MoviesRequests.class);
    }

    @GetMapping("/alternative-titles")
    public ResponseEntity<?> getMovieAlternativeTitles(@PathVariable long movieId, @RequestParam(required = false) Country country) {
        return ResponseEntity.ok(moviesRequests.getMovieAlternativeTitles(movieId, (country == null) ? "" : country.getIsoCode()));
    }

    @GetMapping("/credits")
    public ResponseEntity<?> getMovieCredits(@PathVariable long movieId) {
        return ResponseEntity.ok(moviesRequests.getMovieCredits(movieId));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getMovieDetails(@PathVariable long movieId, @RequestParam(defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(moviesRequests.getMovieDetails(movieId, language.getIsoCode()));
    }

    @GetMapping("/images")
    public ResponseEntity<?> getMovieImages(@PathVariable long movieId, @RequestParam Language language) {
        return ResponseEntity.ok(moviesRequests.getMovieDetails(movieId, language.getIsoCode()));
    }

    @GetMapping("/keywords")
    public ResponseEntity<?> getMovieKeywords(@PathVariable long movieId) {
        return ResponseEntity.ok(moviesRequests.getMovieKeywords(movieId));
    }
}
