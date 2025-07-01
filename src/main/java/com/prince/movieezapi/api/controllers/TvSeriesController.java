package com.prince.movieezapi.api.controllers;

import com.prince.movieezapi.api.models.enums.Language;
import com.prince.movieezapi.api.tmdb.requests.TvSeriesRequests;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@RestController
@RequestMapping("/v1/tv-series")
public class TvSeriesController {
    private final TvSeriesRequests tvSeriesRequests;

    public TvSeriesController(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.tvSeriesRequests = httpServiceProxyFactory.createClient(TvSeriesRequests.class);
    }

    @GetMapping("/{seriesId}/credits")
    public ResponseEntity<?> getTvSeriesCredits(@PathVariable long seriesId,
                                                @RequestParam(defaultValue = "en", required = false) String language) {
        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesCredits(seriesId, language));
    }

    @GetMapping("/{seriesId}/details")
    public ResponseEntity<?> getTvSeriesDetails(@PathVariable long seriesId,
                                                @RequestParam(defaultValue = "en", required = false) String language) {

        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesDetails(seriesId, language));
    }

    @GetMapping("/{seriesId}/images")
    public ResponseEntity<?> getTvSeriesImages(@PathVariable long seriesId,
                                               @RequestParam(defaultValue = "en", required = false) String language) {
        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesImages(seriesId, language));
    }

    @GetMapping("/{seriesId}/keywords")
    public ResponseEntity<?> getTvSeriesKeywords(@PathVariable long seriesId) {
        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesKeywords(seriesId));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestTvSeries() {
        return ResponseEntity.ok(tvSeriesRequests.getLatestTvSeries());
    }

    @GetMapping("/{seriesId}/recommendations")
    public ResponseEntity<?> getTvSeriesRecommendations(@PathVariable long seriesId,
                                                        @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                                        @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesRecommendations(seriesId, language.getIsoCode(), page));
    }

    @GetMapping("/{seriesId}/similar")
    public ResponseEntity<?> getTvSeriesSimilar(@PathVariable long seriesId,
                                                @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                                @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesSimilar(seriesId, language.getIsoCode(), page));
    }

    @GetMapping("/{seriesId}/videos")
    public ResponseEntity<?> getTvSeriesVideos(@PathVariable long seriesId,
                                               @RequestParam(value = "language", defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(tvSeriesRequests.getTvSeriesVideos(seriesId, language.getIsoCode()));
    }
}
