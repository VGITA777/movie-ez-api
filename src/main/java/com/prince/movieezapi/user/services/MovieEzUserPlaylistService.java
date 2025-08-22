package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.dto.MovieEzUserPlaylistDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserPlaylistDtoMapper;
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

    public MovieEzUserPlaylistDto save(MovieEzUserPlaylistModel movieEzUserPlaylistModel) {
        return MovieEzUserPlaylistDtoMapper.toDto(movieEzUserPlaylistRepository.save(movieEzUserPlaylistModel));
    }

    public List<MovieEzUserPlaylistDto> getAllByEmail(String email) {
        return movieEzUserPlaylistRepository.findAllByUserEmail(email).stream().map(MovieEzUserPlaylistDtoMapper::toDto).toList();
    }

    public List<MovieEzUserPlaylistDto> getAllByNameAndEmail(String name, String email) {
        return movieEzUserPlaylistRepository.findAllByNameAndUserEmail(name, email).stream().map(MovieEzUserPlaylistDtoMapper::toDto).toList();
    }

    public void deleteById(long id) {
        movieEzUserPlaylistRepository.deleteById(id);
    }

    public void delete(MovieEzUserPlaylistModel movieEzUserPlaylistModel) {
        movieEzUserPlaylistRepository.delete(movieEzUserPlaylistModel);
    }
}
