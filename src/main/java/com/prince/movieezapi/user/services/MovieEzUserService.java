package com.prince.movieezapi.user.services;

import com.prince.movieezapi.shared.UserSecurityUtils;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.BadCredentialsException;
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

    public MovieEzUserModel save(MovieEzUserModel movieEzUserModel) {
        if (!UserSecurityUtils.isPasswordValid(movieEzUserModel.getPassword())) {
            throw new IllegalArgumentException("Password is not valid");
        }
        movieEzUserModel.setPassword(passwordEncoder.encode(movieEzUserModel.getPassword()));
        return movieEzUserRepository.save(movieEzUserModel);
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

    // TODO: Create a way to invalidate old tokens after password reset
    @Transactional
    public MovieEzUserModel updatePasswordByEmail(String email, String oldPassword, String newPassword) {
        MovieEzUserModel user = findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        if (!UserSecurityUtils.isPasswordValid(newPassword)) {
            throw new IllegalArgumentException("Password is not valid");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Old password does not match");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        return movieEzUserRepository.save(user);
    }
}

