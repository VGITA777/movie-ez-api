package com.prince.movieezapi.media.caching.utils;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import java.time.Duration;

public class MovieCacheConfigurer {

    public static String MOVIE_ALTERNATIVE_TITLES_CACHE = "movieAlternativeTitles";
    public static String MOVIE_CREDITS_CACHE = "movieCredits";
    public static String MOVIE_DETAILS_CACHE = "movieDetails";
    public static String MOVIE_IMAGES_CACHE = "movieImages";
    public static String MOVIE_KEYWORDS_CACHE = "movieKeywords";
    public static String MOVIE_RECOMMENDATIONS_CACHE = "movieRecommendations";
    public static String MOVIE_SIMILAR_CACHE = "movieSimilar";
    public static String MOVIE_VIDEOS_CACHE = "movieVideos";

    public static void configureMovieCaches(CaffeineCacheManager cacheManager) {
        configureMovieAlternativeTitlesCache(cacheManager);
        configureMovieCreditsCache(cacheManager);
        configureMovieDetailsCache(cacheManager);
        configureMovieImagesCache(cacheManager);
        configureMovieKeywordsCache(cacheManager);
        configureMovieRecommendationsCache(cacheManager);
        configureMovieSimilarCache(cacheManager);
        configureMovieVideosCache(cacheManager);
    }

    private static void configureMovieAlternativeTitlesCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_ALTERNATIVE_TITLES_CACHE, Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureMovieCreditsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_CREDITS_CACHE, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureMovieDetailsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_DETAILS_CACHE, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureMovieImagesCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_IMAGES_CACHE, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureMovieKeywordsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_KEYWORDS_CACHE, Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }

    private static void configureMovieRecommendationsCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_RECOMMENDATIONS_CACHE, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(1))
                .build());
    }

    private static void configureMovieSimilarCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_SIMILAR_CACHE, Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofDays(1))
                .build());
    }

    private static void configureMovieVideosCache(CaffeineCacheManager cacheManager) {
        cacheManager.registerCustomCache(MOVIE_VIDEOS_CACHE, Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(Duration.ofDays(7))
                .build());
    }
}
