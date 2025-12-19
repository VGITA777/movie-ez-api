package com.prince.movieezapi.media.api.tmdb.services;

import com.prince.movieezapi.media.api.models.discover.DiscoverMovieModel;
import com.prince.movieezapi.media.api.models.discover.DiscoverTvModel;
import com.prince.movieezapi.media.api.models.inputs.DiscoverMoviesInput;
import com.prince.movieezapi.media.api.models.inputs.DiscoverTvInput;
import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.tmdb.requests.DiscoverRequests;
import com.prince.movieezapi.media.caching.utils.DiscoverCacheConfigurer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service public class DiscoverRequestsService {
    private final DiscoverRequests discoverRequests;

    public DiscoverRequestsService(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.discoverRequests = httpServiceProxyFactory.createClient(DiscoverRequests.class);
    }

    @Cacheable(cacheNames = DiscoverCacheConfigurer.DISCOVER_MOVIES_CACHE)
    public Page<DiscoverMovieModel> discoverMovies(DiscoverMoviesInput input) {
        return discoverRequests.discoverMovies(input.isIncludeAdult(),
                                               input.getLanguage(),
                                               input.getPrimaryReleaseYear(),
                                               input.getPage(),
                                               input.getRegion(),
                                               input.getYear());
    }

    @Cacheable(cacheNames = DiscoverCacheConfigurer.DISCOVER_TV_SERIES_CACHE)
    public Page<DiscoverTvModel> discoverTv(DiscoverTvInput input) {
        return discoverRequests.discoverTvSeries(input.isIncludeAdult(),
                                                 input.getLanguage(),
                                                 input.getFirstAirDateYear(),
                                                 input.getPage());
    }
}
