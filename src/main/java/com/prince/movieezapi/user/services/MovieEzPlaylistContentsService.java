package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzPlaylistContentsModel;
import com.prince.movieezapi.user.repository.MovieEzPlaylistContentsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MovieEzPlaylistContentsService {
    private final MovieEzPlaylistContentsRepository movieEzPlaylistContentsRepository;

    public MovieEzPlaylistContentsService(MovieEzPlaylistContentsRepository movieEzPlaylistContentsRepository) {
        this.movieEzPlaylistContentsRepository = movieEzPlaylistContentsRepository;
    }

    public MovieEzPlaylistContentsModel save(MovieEzPlaylistContentsModel movieEzPlaylistContentsModel) {
        return movieEzPlaylistContentsRepository.save(movieEzPlaylistContentsModel);
    }

    public Optional<MovieEzPlaylistContentsModel> get(UUID id) {
        return movieEzPlaylistContentsRepository.findById(id);
    }

    public void delete(UUID id) {
        movieEzPlaylistContentsRepository.deleteById(id);
    }

    public void delete(MovieEzPlaylistContentsModel movieEzPlaylistContentsModel) {
        movieEzPlaylistContentsRepository.delete(movieEzPlaylistContentsModel);
    }
}
