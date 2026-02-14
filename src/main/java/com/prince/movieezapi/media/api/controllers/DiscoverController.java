package com.prince.movieezapi.media.api.controllers;

import com.prince.movieezapi.media.api.models.discover.DiscoverMovieModel;
import com.prince.movieezapi.media.api.models.discover.DiscoverTvModel;
import com.prince.movieezapi.media.api.models.inputs.DiscoverMoviesInput;
import com.prince.movieezapi.media.api.models.inputs.DiscoverTvInput;
import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.tmdb.services.DiscoverRequestsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media/discover")
public class DiscoverController {

  private final DiscoverRequestsService discoverRequestsService;

  public DiscoverController(DiscoverRequestsService discoverRequestsService) {
    this.discoverRequestsService = discoverRequestsService;
  }

  @GetMapping("/movies")
  public ResponseEntity<Page<DiscoverMovieModel>> discoverMovies(
      @Valid
      @ModelAttribute
      DiscoverMoviesInput input
  ) {
    return ResponseEntity.ok(discoverRequestsService.discoverMovies(input));
  }

  @GetMapping("/tv")
  public ResponseEntity<Page<DiscoverTvModel>> discoverTv(
      @Valid
      @ModelAttribute
      DiscoverTvInput input
  ) {
    return ResponseEntity.ok(discoverRequestsService.discoverTv(input));
  }
}
