package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.dto.MovieEzUserDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserDtoMapper;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieEzUserService {
    private final MovieEzUserRepository movieEzUserRepository;
    private final PasswordEncoder passwordEncoder;

    public MovieEzUserService(MovieEzUserRepository movieEzUserRepository, PasswordEncoder passwordEncoder) {
        this.movieEzUserRepository = movieEzUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<MovieEzUserDto> findByEmail(String email) {
        Optional<MovieEzUserModel> user = movieEzUserRepository.findByEmail(email);
        return user.map(MovieEzUserDtoMapper::toDto);
    }

    public Optional<MovieEzUserDto> findByUsername(String username) {
        Optional<MovieEzUserModel> user = movieEzUserRepository.findByUsername(username);
        return user.map(MovieEzUserDtoMapper::toDto);
    }

    public Optional<MovieEzUserModel> save(MovieEzUserModel movieEzUserModel) {
        movieEzUserModel.setPassword(passwordEncoder.encode(movieEzUserModel.getPassword()));
        return Optional.of(movieEzUserRepository.save(movieEzUserModel));
    }

    public void delete(MovieEzUserModel movieEzUserModel) {
        movieEzUserRepository.delete(movieEzUserModel);
    }

    public void deleteById(long id) {
        movieEzUserRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return movieEzUserRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return movieEzUserRepository.existsByUsername(username);
    }
}

