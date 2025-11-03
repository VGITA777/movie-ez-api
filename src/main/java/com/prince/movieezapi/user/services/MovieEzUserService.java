package com.prince.movieezapi.user.services;

import com.prince.movieezapi.security.services.UserSessionService;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class MovieEzUserService {
    private final MovieEzUserRepository movieEzUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionService userSessionService;

    public MovieEzUserService(MovieEzUserRepository movieEzUserRepository, PasswordEncoder passwordEncoder, UserSessionService userSessionService) {
        this.movieEzUserRepository = movieEzUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSessionService = userSessionService;
    }

    public Optional<MovieEzUserModel> findByEmail(String email) {
        return movieEzUserRepository.findByEmail(email);
    }

    public Optional<MovieEzUserModel> findByUsername(String username) {
        return movieEzUserRepository.findByUsername(username);
    }

    @Transactional
    public MovieEzUserModel save(MovieEzUserModel movieEzUserModel) {
        movieEzUserModel.setPassword(passwordEncoder.encode(movieEzUserModel.getPassword()));
        return movieEzUserRepository.save(movieEzUserModel);
    }

    public void delete(MovieEzUserModel movieEzUserModel) {
        movieEzUserRepository.delete(movieEzUserModel);
    }

    public void deleteById(UUID id) {
        movieEzUserRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return movieEzUserRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return movieEzUserRepository.existsByUsername(username);
    }

    @Transactional
    public MovieEzUserModel updatePasswordByEmail(String email, String oldPassword, String newPassword, HttpSession httpSession, boolean invalidateOtherSessions) {
        MovieEzUserModel user = findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BadCredentialsException("Old password does not match");
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        MovieEzUserModel saved = movieEzUserRepository.save(user);
        if (invalidateOtherSessions) {
            userSessionService.deleteAllSessionsByUsernameExcludeSessionId(user.getEmail(), httpSession.getId());
        }
        return saved;
    }
}

