package com.prince.movieezapi.api.tmdb.services;

import com.prince.movieezapi.api.models.shared.CreditsModel;
import com.prince.movieezapi.api.models.shared.ImagesModel;
import com.prince.movieezapi.api.models.shared.VideosModel;
import com.prince.movieezapi.api.models.tvseries.*;
import com.prince.movieezapi.api.tmdb.requests.TvSeriesRequests;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Service
public class TvSeriesRequestsService {
    private final TvSeriesRequests tvSeriesRequests;

    public TvSeriesRequestsService(HttpServiceProxyFactory httpServiceProxyFactory) {
        this.tvSeriesRequests = httpServiceProxyFactory.createClient(TvSeriesRequests.class);
    }

    @Cacheable(cacheNames = "tvSeriesCredits")
    public CreditsModel getTvSeriesCredits(long seriesId, String language) {
        return tvSeriesRequests.getTvSeriesCredits(seriesId, language);
    }

    @Cacheable(cacheNames = "tvSeriesDetails")
    public TvSeriesDetailsModel getTvSeriesDetails(long seriesId, String language) {
        return tvSeriesRequests.getTvSeriesDetails(seriesId, language);
    }

    @Cacheable(cacheNames = "tvSeriesImages")
    public ImagesModel getTvSeriesImages(long seriesId, String language) {
        return tvSeriesRequests.getTvSeriesImages(seriesId, language);
    }

    @Cacheable(cacheNames = "tvSeriesKeywords")
    public TvSeriesKeywordsModel getTvSeriesKeywords(long seriesId) {
        return tvSeriesRequests.getTvSeriesKeywords(seriesId);
    }

    public TvSeriesLatestModel getLatestTvSeries() {
        return tvSeriesRequests.getLatestTvSeries();
    }

    @Cacheable(cacheNames = "tvSeriesRecommendations")
    public TvSeriesRecommendationsModel getTvSeriesRecommendations(long seriesId, String language, int page) {
        return tvSeriesRequests.getTvSeriesRecommendations(seriesId, language, page);
    }

    @Cacheable(cacheNames = "tvSeriesSimilar")
    public TvSeriesSimilarModel getTvSeriesSimilar(long seriesId, String language, int page) {
        return tvSeriesRequests.getTvSeriesSimilar(seriesId, language, page);
    }

    @Cacheable(cacheNames = "tvSeriesVideos")
    public VideosModel getTvSeriesVideos(long seriesId, String language) {
        return tvSeriesRequests.getTvSeriesVideos(seriesId, language);
    }
}

