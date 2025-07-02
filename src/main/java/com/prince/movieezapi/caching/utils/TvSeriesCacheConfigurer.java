package com.prince.movieezapi.caching.utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.time.Duration;

public class TvSeriesCacheConfigurer {
    public static final String TV_SERIES_CREDITS = "tvSeriesCredits";
    public static final String TV_SERIES_DETAILS = "tvSeriesDetails";
    public static final String TV_SERIES_IMAGES = "tvSeriesImages";
    public static final String TV_SERIES_KEYWORDS = "tvSeriesKeywords";
    public static final String TV_SERIES_RECOMMENDATIONS = "tvSeriesRecommendations";
    public static final String TV_SERIES_SIMILAR = "tvSeriesSimilar";
    public static final String TV_SERIES_VIDEOS = "tvSeriesVideos";

    public static void configureTvSeriesCaches(CaffeineCacheManager cacheManager) {
        configureTvSeriesCreditsCache(cacheManager);
        configureTvSeriesDetailsCache(cacheManager);
        configureTvSeriesImagesCache(cacheManager);
        configureTvSeriesKeywordsCache(cacheManager);
        configureTvSeriesRecommendationsCache(cacheManager);
        configureTvSeriesSimilarCache(cacheManager);
        configureTvSeriesVideosCache(cacheManager);
    }

    private static void configureTvSeriesCreditsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_CREDITS, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureTvSeriesDetailsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_DETAILS, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureTvSeriesImagesCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_IMAGES, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureTvSeriesKeywordsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_KEYWORDS, Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureTvSeriesRecommendationsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_RECOMMENDATIONS, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureTvSeriesSimilarCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_SIMILAR, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureTvSeriesVideosCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(TV_SERIES_VIDEOS, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }
}
