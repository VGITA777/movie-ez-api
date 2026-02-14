package com.prince.movieezapi.media.api.controllers;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.prince.movieezapi.media.api.models.discover.DiscoverMovieModel;
import com.prince.movieezapi.media.api.models.discover.DiscoverTvModel;
import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.tmdb.services.DiscoverRequestsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DiscoverControllerTest {

  @Mock
  private DiscoverRequestsService discoverRequestsService;

  @InjectMocks
  private DiscoverController discoverController;

  @Test
  void testDiscoverMoviesReturnsPageOfDiscoverMovies() {
    var pageObj = new Page<DiscoverMovieModel>();
    var expected = ResponseEntity.ok(pageObj);

    when(discoverRequestsService.discoverMovies(Mockito.any())).thenReturn(pageObj);
    var discoverMovieModelPage = discoverController.discoverMovies(Mockito.any());

    verify(discoverRequestsService).discoverMovies(Mockito.any());
    assertNotNull(discoverMovieModelPage);
    assertEquals(expected, discoverMovieModelPage);
  }

  @Test
  void testDiscoverTvReturnsPageOfDiscoverTv() {
    var pageObj = new Page<DiscoverTvModel>();
    ResponseEntity<Page<DiscoverTvModel>> expected = ResponseEntity.ok(pageObj);

    when(discoverRequestsService.discoverTv(Mockito.any())).thenReturn(pageObj);
    ResponseEntity<Page<DiscoverTvModel>> discoverTvModelPage = discoverController.discoverTv(Mockito.any());

    verify(discoverRequestsService).discoverTv(Mockito.any());
    assertNotNull(discoverTvModelPage);
    assertEquals(expected, discoverTvModelPage);
  }
}