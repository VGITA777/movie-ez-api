package com.prince.movieezapi.media.api.tmdb.services;

import com.prince.movieezapi.media.api.models.discover.DiscoverMovieModel;
import com.prince.movieezapi.media.api.models.discover.DiscoverTvModel;
import com.prince.movieezapi.media.api.models.inputs.DiscoverMoviesInput;
import com.prince.movieezapi.media.api.models.inputs.DiscoverTvInput;
import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.tmdb.requests.DiscoverRequests;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class DiscoverRequestsService {
    private final DiscoverRequests discoverRequests;

    public DiscoverRequestsService(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.discoverRequests = httpServiceProxyFactory.createClient(DiscoverRequests.class);
    }

    @Cacheable(cacheNames = "discoverMovies")
    public Page<DiscoverMovieModel> discoverMovies(DiscoverMoviesInput input) {
        return discoverRequests.discoverMovies(
                input.isIncludeAdult(),
                input.getLanguage(),
                input.getPrimaryReleaseYear(),
                input.getPage(),
                input.getRegion(),
                input.getYear()
        );
    }

    @Cacheable(cacheNames = "discoverTvSeries")
    public Page<DiscoverTvModel> discoverTv(DiscoverTvInput input) {
        return discoverRequests.discoverTvSeries(
                input.isIncludeAdult(),
                input.getLanguage(),
                input.getFirstAirDateYear(),
                input.getPage()
        );
    }
}
