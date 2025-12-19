package com.prince.movieezapi.media.api.controllers;

import com.prince.movieezapi.media.api.models.discover.DiscoverMovieModel;
import com.prince.movieezapi.media.api.models.discover.DiscoverTvModel;
import com.prince.movieezapi.media.api.models.inputs.DiscoverMoviesInput;
import com.prince.movieezapi.media.api.models.inputs.DiscoverTvInput;
import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.tmdb.services.DiscoverRequestsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/media/v1/discover") public class DiscoverController {

  private final DiscoverRequestsService discoverRequestsService;

  public DiscoverController(DiscoverRequestsService discoverRequestsService) {
    this.discoverRequestsService = discoverRequestsService;
  }

  @GetMapping("/movies")
  public Page<DiscoverMovieModel> discoverMovies(@ModelAttribute DiscoverMoviesInput input) {
    return discoverRequestsService.discoverMovies(input);
  }

  @GetMapping("/tv")
  public Page<DiscoverTvModel> discoverTv(@ModelAttribute DiscoverTvInput input) {
    return discoverRequestsService.discoverTv(input);
  }
}
