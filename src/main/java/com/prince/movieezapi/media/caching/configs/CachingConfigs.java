package com.prince.movieezapi.media.caching.configs;

import com.prince.movieezapi.media.caching.utils.MovieCacheConfigurer;
import com.prince.movieezapi.media.caching.utils.TvSeriesCacheConfigurer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CachingConfigs {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setAllowNullValues(false);
        cacheManager.setAsyncCacheMode(true);
        MovieCacheConfigurer.configureMovieCaches(cacheManager);
        TvSeriesCacheConfigurer.configureTvSeriesCaches(cacheManager);
        return cacheManager;
    }
}
