package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import com.prince.movieezapi.user.repository.MovieEzUserPlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieEzUserPlaylistService {
    private final MovieEzUserPlaylistRepository movieEzUserPlaylistRepository;


    public MovieEzUserPlaylistService(MovieEzUserPlaylistRepository movieEzUserPlaylistRepository) {
        this.movieEzUserPlaylistRepository = movieEzUserPlaylistRepository;
    }

    public List<MovieEzUserPlaylistModel> getAllByEmail(String email) {
        return movieEzUserPlaylistRepository.findAllByUserEmail(email);
    }
}
