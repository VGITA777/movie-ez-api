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

    public Optional<MovieEzUserModel> findById(UUID id) {
        return movieEzUserRepository.findById(id);
    }

    @Transactional
    public MovieEzUserModel save(MovieEzUserModel movieEzUserModel) {
        movieEzUserModel.setPassword(passwordEncoder.encode(movieEzUserModel.getPassword()));
        return movieEzUserRepository.save(movieEzUserModel);
    }

    @Transactional
    public void delete(UUID uuid, String password) {
        MovieEzUserModel movieEzUserModel = findById(uuid).orElseThrow(() -> new UserNotFoundException("Failed to delete user with UUID: '" + uuid + "' because user does not exists"));
        if (!passwordEncoder.matches(password, movieEzUserModel.getPassword())) {
            throw new BadCredentialsException("Failed to delete user with id: '" + uuid + "' because password does not match");
        }
        movieEzUserRepository.delete(movieEzUserModel);
        userSessionService.deleteAllSessionsByPrincipalName(movieEzUserModel.getId());
    }

    public boolean existsByEmail(String email) {
        return movieEzUserRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return movieEzUserRepository.existsByUsername(username);
    }

    @Transactional
    public MovieEzUserModel updatePasswordById(UUID id, String oldPassword, String newPassword, HttpSession httpSession, boolean invalidateSessions, boolean invalidateAllSessions) {
        MovieEzUserModel user = findById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: '" + id + "'"));
        String userPassword = user.getPassword();
        if (!passwordEncoder.matches(oldPassword, userPassword)) {
            throw new BadCredentialsException("Old password does not match");
        }
        if (passwordEncoder.matches(newPassword, userPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedNewPassword);
        MovieEzUserModel saved = movieEzUserRepository.save(user);

        if (invalidateSessions) {
            if (invalidateAllSessions) {
                userSessionService.deleteAllSessionsByPrincipalName(user.getId());
            } else {
                userSessionService.deleteAllSessionsByPrincipalNameExcludeSessionId(user.getId(), httpSession.getId());
            }
        }
        return saved;
    }

    @Transactional
    public MovieEzUserModel updateUsernameById(UUID uuid, String username) {
        MovieEzUserModel user = findById(uuid).orElseThrow(() -> new UserNotFoundException("User not found with email: '" + uuid + "'"));
        user.setUsername(username);
        return movieEzUserRepository.save(user);
    }
}

