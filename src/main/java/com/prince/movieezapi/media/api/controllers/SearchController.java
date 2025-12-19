package com.prince.movieezapi.media.api.controllers;

import com.prince.movieezapi.media.api.models.inputs.SearchMovieInput;
import com.prince.movieezapi.media.api.models.inputs.SearchMultiInput;
import com.prince.movieezapi.media.api.models.inputs.SearchTvInput;
import com.prince.movieezapi.media.api.tmdb.services.SearchRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/media/v1/search") public class SearchController {
    private final SearchRequestsService searchRequestsService;

    @Autowired
    public SearchController(SearchRequestsService searchRequestsService) {
        this.searchRequestsService = searchRequestsService;
    }

    @GetMapping("/movie")
    public ResponseEntity<?> searchMovie(@ModelAttribute SearchMovieInput input) {
        return ResponseEntity.ok(searchRequestsService.searchMovies(input));
    }

    @GetMapping("/tv")
    public ResponseEntity<?> searchTvSeries(@ModelAttribute SearchTvInput input) {
        return ResponseEntity.ok(searchRequestsService.searchTvSeries(input));
    }

    @GetMapping("/multi")
    public ResponseEntity<?> searchMulti(@ModelAttribute SearchMultiInput input) {
        return ResponseEntity.ok(searchRequestsService.searchMulti(input));
    }
}
