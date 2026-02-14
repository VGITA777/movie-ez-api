package com.prince.movieezapi.media.api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.prince.movieezapi.media.api.models.enums.Language;
import com.prince.movieezapi.media.api.models.movies.MovieAlternativeTitlesModel;
import com.prince.movieezapi.media.api.models.movies.MovieDetailsModel;
import com.prince.movieezapi.media.api.models.movies.MovieKeywordsModel;
import com.prince.movieezapi.media.api.models.movies.MovieLatestModel;
import com.prince.movieezapi.media.api.models.movies.MovieRecommendationsModel;
import com.prince.movieezapi.media.api.models.movies.MovieSimilarModel;
import com.prince.movieezapi.media.api.models.shared.CreditsModel;
import com.prince.movieezapi.media.api.models.shared.ImagesModel;
import com.prince.movieezapi.media.api.models.shared.VideosModel;
import com.prince.movieezapi.media.api.tmdb.services.MovieRequestsService;
import com.prince.movieezapi.security.configs.SecurityConfigs;
import com.prince.movieezapi.security.ratelimit.RateLimiterEntry;
import com.prince.movieezapi.security.ratelimit.RateLimiterService;
import com.prince.movieezapi.shared.utilities.BasicUtils;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import io.github.resilience4j.ratelimiter.RateLimiter;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MovieController.class)
@Import(SecurityConfigs.class)
class MovieControllerWebTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MovieRequestsService movieRequestsService;

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
  void testGetMovieAlternative_ExpectSuccess() throws Exception {
    var id = 1L;
    var expectedAlternativeTitlesModel = new MovieAlternativeTitlesModel(id, List.of());

    when(movieRequestsService.getMovieAlternativeTitles(anyLong(), anyString())).thenReturn(
        expectedAlternativeTitlesModel);

    mockMvc
        .perform(get("/media/movie/%s/alternative-titles".formatted(id)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1L))
        .andExpect(jsonPath("$.titles").isArray());
  }

  @Test
  void testGetMovieAlternative_InvalidId_ExpectBadRequest() throws Exception {
    var id = "abc";
    mockMvc
        .perform(get("/media/movie/%s/alternative-titles".formatted(id)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieCredits_ExpectSuccess() throws Exception {
    var id = 1L;
    var expected = new CreditsModel(id, List.of(), List.of());

    when(movieRequestsService.getMovieCredits(anyLong(), anyString())).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/credits".formatted(id)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.cast").isArray())
        .andExpect(jsonPath("$.crew").isArray());
  }

  @Test
  void testGetMovieDetails_ExpectSuccess() throws Exception {
    var id = 1L;
    var language = Language.ENGLISH;
    var expected = MovieDetailsModel
        .builder()
        .build();

    when(movieRequestsService.getMovieDetails(id, language.getIsoCode())).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/details".formatted(id)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMovieDetails_InvalidId_ExpectBadRequest() throws Exception {
    var id = "abc";
    mockMvc
        .perform(get("/media/movie/%s/details".formatted(id)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieImages_ExpectSuccess() throws Exception {
    var id = 1L;
    var language = Language.ENGLISH;
    var expected = new ImagesModel(id, List.of(), List.of(), List.of());

    when(movieRequestsService.getMovieImages(id, language.getIsoCode())).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/images".formatted(id)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void tetGetMovieImages_InvalidId_ExpectBadRequest() throws Exception {
    var id = "abc";
    mockMvc
        .perform(get("/media/movie/%s/images".formatted(id)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieKeywords_ExpectSuccess() throws Exception {
    var id = 1L;
    var expected = new MovieKeywordsModel(id, List.of());

    when(movieRequestsService.getMovieKeywords(id)).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/keywords".formatted(id)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMovieKeywords_InvalidId_ExpectBadRequest() throws Exception {
    var id = "abc";
    mockMvc
        .perform(get("/media/movie/%s/keywords".formatted(id)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetLatest_ExpectSuccess() throws Exception {
    var expected = new MovieLatestModel();

    when(movieRequestsService.getLatest()).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/latest"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMovieRecommendations_ExpectSuccess() throws Exception {
    var id = 1L;
    var language = Language.ENGLISH;
    var page = 1;
    var expected = new MovieRecommendationsModel();

    when(movieRequestsService.getMovieRecommendations(id, language.getIsoCode(), page)).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/recommendations?page=%s".formatted(id, page)))
        .andExpect(status().isOk());
  }

  @Test
  void testGetMovieRecommendations_BadId_ExpectBadRequest() throws Exception {
    var id = "abc";
    mockMvc
        .perform(get("/media/movie/%s/recommendations".formatted(id)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieRecommendations_BadPageCharacters_ExpectBadRequest() throws Exception {
    var id = 1L;
    var page = "abc";
    mockMvc
        .perform(get("/media/movie/%s/recommendations?page=%s".formatted(id, page)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieRecommendations_BadPageNegativeNumber_ExpectBadRequest() throws Exception {
    var id = 1L;
    var page = -1;
    mockMvc
        .perform(get("/media/movie/%s/recommendations?page=%s".formatted(id, page)))
        .andExpect(status().isBadRequest());
  }


  @Test
  void testGetMovieSimilar_ExpectSuccess() throws Exception {
    var id = 1L;
    var language = Language.ENGLISH;
    var page = 1;
    var expected = new MovieSimilarModel();

    when(movieRequestsService.getMovieSimilar(id, language.getIsoCode(), page)).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/similar?page=%s".formatted(id, page)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMovieSimilar_BadId_ExpectBadRequest() throws Exception {
    var id = "abc";
    mockMvc
        .perform(get("/media/movie/%s/similar".formatted(id)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieSimilar_BadPageCharacter_ExpectBadRequest() throws Exception {
    var id = 1L;
    var language = Language.ENGLISH;
    var page = "abc";
    var expected = new MovieSimilarModel();

    when(movieRequestsService.getMovieSimilar(id, language.getIsoCode(), 1)).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/similar?page=%s".formatted(id, page)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetMovieSimilar_BadPageNegativeNumber_ExpectBadRequest() throws Exception {
    var id = 1L;
    var page = -1;
    mockMvc
        .perform(get("/media/movie/%s/similar?page=%s".formatted(id, page)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetMovieVideos_ExpectSuccess() throws Exception {
    var id = 1L;
    var language = Language.ENGLISH;
    var expected = new VideosModel();

    when(movieRequestsService.getMovieVideos(id, language.getIsoCode())).thenReturn(expected);

    mockMvc
        .perform(get("/media/movie/%s/videos?language=%s".formatted(id, language)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }
}