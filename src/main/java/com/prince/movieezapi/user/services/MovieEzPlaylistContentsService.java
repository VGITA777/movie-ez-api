package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzPlaylistContentModel;
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

    public MovieEzPlaylistContentModel save(MovieEzPlaylistContentModel movieEzPlaylistContentModel) {
        return movieEzPlaylistContentsRepository.save(movieEzPlaylistContentModel);
    }

    public Optional<MovieEzPlaylistContentModel> get(UUID id) {
        return movieEzPlaylistContentsRepository.findById(id);
    }

    public void delete(UUID id) {
        movieEzPlaylistContentsRepository.deleteById(id);
    }

    public void delete(MovieEzPlaylistContentModel movieEzPlaylistContentModel) {
        movieEzPlaylistContentsRepository.delete(movieEzPlaylistContentModel);
    }
}
