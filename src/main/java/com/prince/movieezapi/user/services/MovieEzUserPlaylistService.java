package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import com.prince.movieezapi.user.repository.MovieEzUserPlaylistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service public class MovieEzUserPlaylistService {
    private final MovieEzUserPlaylistRepository movieEzUserPlaylistRepository;


    public MovieEzUserPlaylistService(MovieEzUserPlaylistRepository movieEzUserPlaylistRepository) {
        this.movieEzUserPlaylistRepository = movieEzUserPlaylistRepository;
    }

    public MovieEzUserPlaylistModel save(MovieEzUserPlaylistModel movieEzUserPlaylistModel) {
        return movieEzUserPlaylistRepository.save(movieEzUserPlaylistModel);
    }

    public List<MovieEzUserPlaylistModel> getAllByUserId(UUID userId) {
        return movieEzUserPlaylistRepository.findAllByUserId(userId);
    }

    public Optional<MovieEzUserPlaylistModel> getByNameAndUserId(String playlistName, UUID userId) {
        return movieEzUserPlaylistRepository.findByNameAndUserId(playlistName, userId);
    }

    @Transactional
    public boolean delete(String playlistName, UUID userId) {
        return movieEzUserPlaylistRepository.deleteByNameAndUserId(playlistName, userId) > 0;
    }
}
