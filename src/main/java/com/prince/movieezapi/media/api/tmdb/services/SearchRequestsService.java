package com.prince.movieezapi.media.api.tmdb.services;

import com.prince.movieezapi.media.api.models.search.SearchMovieResultsModel;
import com.prince.movieezapi.media.api.models.search.SearchMultiResultsModel;
import com.prince.movieezapi.media.api.models.search.SearchTvSeriesResultsModel;
import com.prince.movieezapi.media.api.tmdb.requests.SearchRequests;
import com.prince.movieezapi.media.caching.utils.SearchCacheConfigurer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class SearchRequestsService {
    private final SearchRequests searchRequests;

    public SearchRequestsService(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.searchRequests = httpServiceProxyFactory.createClient(SearchRequests.class);
    }

    @Cacheable(cacheNames = SearchCacheConfigurer.SEARCH_MOVIE_RESULTS_CACHE)
    public SearchMovieResultsModel searchMovies(String query, boolean includeAdult, String language, Integer primaryReleaseYear, int page, String region, Integer year) {
        return searchRequests.searchMovies(query, includeAdult, language, primaryReleaseYear, page, region, year);
    }

    @Cacheable(cacheNames = SearchCacheConfigurer.SEARCH_TV_SERIES_RESULTS_CACHE)
    public SearchTvSeriesResultsModel searchTvSeries(String query, Integer firstAirDateYear, boolean includeAdult, String language, int page, Integer year) {
        return searchRequests.searchTvSeries(query, firstAirDateYear, includeAdult, language, page, year);
    }

    @Cacheable(cacheNames = SearchCacheConfigurer.SEARCH_MULTI_RESULTS_CACHE)
    public SearchMultiResultsModel searchMulti(String query, boolean includeAdult, String language, int page) {
        return searchRequests.searchMulti(query, includeAdult, language, page);
    }
}

