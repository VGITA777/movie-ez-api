package com.prince.movieezapi.user.services;

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

    public Optional<MovieEzUserModel> findByEmail(String email) {
        return movieEzUserRepository.findByEmail(email);
    }

    public Optional<MovieEzUserModel> findByUsername(String username) {
        return movieEzUserRepository.findByUsername(username);
    }

    public Optional<MovieEzUserModel> save(MovieEzUserModel movieEzUserModel) {
        movieEzUserModel.setPassword(passwordEncoder.encode(movieEzUserModel.getPassword()));
        return Optional.of(movieEzUserRepository.save(movieEzUserModel));
    }

    public boolean existsByEmail(String email) {
        return movieEzUserRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return movieEzUserRepository.existsByUsername(username);
    }
}

