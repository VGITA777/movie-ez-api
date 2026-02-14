package com.prince.movieezapi.media.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.prince.movieezapi.media.api.models.discover.DiscoverMovieModel;
import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.tmdb.services.DiscoverRequestsService;
import com.prince.movieezapi.security.configs.SecurityConfigs;
import com.prince.movieezapi.security.ratelimit.RateLimiterEntry;
import com.prince.movieezapi.security.ratelimit.RateLimiterService;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DiscoverController.class)
@Import(SecurityConfigs.class)
class DiscoverControllerWebTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private DiscoverRequestsService discoverRequestsService;

  @MockitoBean
  private RateLimiterService rateLimiterService;

  @MockitoBean
  private MovieEzUserRepository movieEzUserRepository;

  @MockitoBean
  private BasicUtils basicUtils;


  @BeforeEach
  void setUp() {
    RateLimiter mockedRateLimiter = Mockito.mock(RateLimiter.class);
    when(mockedRateLimiter.acquirePermission()).thenReturn(true);
    RateLimiterEntry mockedEntry = new RateLimiterEntry(null, null, mockedRateLimiter);
    when(rateLimiterService.get(any())).thenReturn(mockedEntry);
    when(rateLimiterService.create(any())).thenReturn(mockedEntry);
  }

  @Test
  void testDiscoverMovies_ExpectSuccess() throws Exception {
    var expect = new Page<DiscoverMovieModel>();

    when(discoverRequestsService.discoverMovies(any())).thenReturn(expect);

    mockMvc
        .perform(get("/media/discover/movies"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testDiscoverMovies_BadIncludeAdultString_ExpectBadRequest() throws Exception {
    var year = "abc";
    mockMvc
        .perform(get("/media/discover/movies?includeAdult=%s".formatted(year)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testDiscoverMovies_BadPageString_ExpectBadRequest() throws Exception {
    var page = "abc";
    mockMvc
        .perform(get("/media/discover/movies?page=%s".formatted(page)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testDiscoverMovies_BadPageNegativeNumber_ExpectBadRequest() throws Exception {
    var page = -1;
    mockMvc
        .perform(get("/media/discover/movies?page=%s".formatted(page)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testDiscoverMovies_BadPageZeroNumber_ExpectBadRequest() throws Exception {
    var page = 0;
    mockMvc
        .perform(get("/media/discover/movies?page=%s".formatted(page)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testDiscoverMovies_BadYearString_ExpectBadRequest() throws Exception {
    var year = "abc";
    mockMvc
        .perform(get("/media/discover/movies?year=%s".formatted(year)))
        .andExpect(status().isBadRequest());
  }
}