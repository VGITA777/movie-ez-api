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

    public MovieEzUserPlaylistModel save(MovieEzUserPlaylistModel movieEzUserPlaylistModel) {
        return movieEzUserPlaylistRepository.save(movieEzUserPlaylistModel);
    }

    public List<MovieEzUserPlaylistModel> getAllByEmail(String email) {
        return movieEzUserPlaylistRepository.findAllByUserEmail(email);
    }

    public List<MovieEzUserPlaylistModel> getAllByNameAndEmail(String name, String email) {
        return movieEzUserPlaylistRepository.findAllByNameAndUserEmail(name, email);
    }

    public void deleteById(long id) {
        movieEzUserPlaylistRepository.deleteById(id);
    }

    public void delete(MovieEzUserPlaylistModel movieEzUserPlaylistModel) {
        movieEzUserPlaylistRepository.delete(movieEzUserPlaylistModel);
    }
}
