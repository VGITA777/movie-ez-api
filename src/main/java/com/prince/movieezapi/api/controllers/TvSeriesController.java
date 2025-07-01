package com.prince.movieezapi.api.controllers;

import com.prince.movieezapi.api.models.enums.Language;
import com.prince.movieezapi.api.tmdb.services.TvSeriesRequestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tv-series")
public class TvSeriesController {
    private final TvSeriesRequestsService tvSeriesRequestsService;

    @Autowired
    public TvSeriesController(TvSeriesRequestsService tvSeriesRequestsService) {
        this.tvSeriesRequestsService = tvSeriesRequestsService;
    }

    @GetMapping("/{seriesId}/credits")
    public ResponseEntity<?> getTvSeriesCredits(@PathVariable long seriesId,
                                                @RequestParam(defaultValue = "en", required = false) String language) {
        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesCredits(seriesId, language));
    }

    @GetMapping("/{seriesId}/details")
    public ResponseEntity<?> getTvSeriesDetails(@PathVariable long seriesId,
                                                @RequestParam(defaultValue = "en", required = false) String language) {

        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesDetails(seriesId, language));
    }

    @GetMapping("/{seriesId}/images")
    public ResponseEntity<?> getTvSeriesImages(@PathVariable long seriesId,
                                               @RequestParam(defaultValue = "en", required = false) String language) {
        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesImages(seriesId, language));
    }

    @GetMapping("/{seriesId}/keywords")
    public ResponseEntity<?> getTvSeriesKeywords(@PathVariable long seriesId) {
        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesKeywords(seriesId));
    }

    @GetMapping("/latest")
    public ResponseEntity<?> getLatestTvSeries() {
        return ResponseEntity.ok(tvSeriesRequestsService.getLatestTvSeries());
    }

    @GetMapping("/{seriesId}/recommendations")
    public ResponseEntity<?> getTvSeriesRecommendations(@PathVariable long seriesId,
                                                        @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                                        @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesRecommendations(seriesId, language.getIsoCode(), page));
    }

    @GetMapping("/{seriesId}/similar")
    public ResponseEntity<?> getTvSeriesSimilar(@PathVariable long seriesId,
                                                @RequestParam(value = "language", defaultValue = "en", required = false) Language language,
                                                @RequestParam(defaultValue = "1", required = false) int page) {
        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesSimilar(seriesId, language.getIsoCode(), page));
    }

    @GetMapping("/{seriesId}/videos")
    public ResponseEntity<?> getTvSeriesVideos(@PathVariable long seriesId,
                                               @RequestParam(value = "language", defaultValue = "en", required = false) Language language) {
        return ResponseEntity.ok(tvSeriesRequestsService.getTvSeriesVideos(seriesId, language.getIsoCode()));
    }
}
