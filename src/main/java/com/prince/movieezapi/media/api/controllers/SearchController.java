package com.prince.movieezapi.media.api.controllers;

import com.prince.movieezapi.media.api.models.enums.Language;
import com.prince.movieezapi.media.api.tmdb.services.SearchRequestsService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RateLimiter(name = "searchEndpoints")
@RestController
@RequestMapping("/media/v1/search")
public class SearchController {
    private final SearchRequestsService searchRequestsService;

    @Autowired
    public SearchController(SearchRequestsService searchRequestsService) {
        this.searchRequestsService = searchRequestsService;
    }

    @GetMapping("/movie")
    public ResponseEntity<?> searchMovie(@RequestParam(value = "query") String query,
                                         @RequestParam(value = "include_adult", defaultValue = "false", required = false) boolean includeAdult,
                                         @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                         @RequestParam(value = "primary_release_year", required = false) Integer primaryReleaseYear,
                                         @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                         @RequestParam(value = "region", required = false) String region,
                                         @RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok(searchRequestsService.searchMovies(query, includeAdult, language.getIsoCode(), primaryReleaseYear, page, region, year));
    }

    @GetMapping("/tv")
    public ResponseEntity<?> searchTvSeries(@RequestParam(value = "query") String query,
                                            @RequestParam(value = "first_air_date_year", required = false) Integer firstAirDateYear,
                                            @RequestParam(value = "include_adult", defaultValue = "false", required = false) boolean includeAdult,
                                            @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                            @RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok(searchRequestsService.searchTvSeries(query, firstAirDateYear, includeAdult, language.getIsoCode(), page, year));
    }

    @GetMapping("/multi")
    public ResponseEntity<?> searchMulti(@RequestParam(value = "query") String query,
                                         @RequestParam(value = "include_adult", defaultValue = "false", required = false) boolean includeAdult,
                                         @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                         @RequestParam(value = "page", defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(searchRequestsService.searchMulti(query, includeAdult, language.getIsoCode(), page));
    }
}
