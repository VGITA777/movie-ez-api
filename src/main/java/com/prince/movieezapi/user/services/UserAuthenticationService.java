package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserAuthenticationService {
    private final PasswordEncoder bCryptPasswordEncoder;
    private final MovieEzUserRepository movieEzUserService;

    public UserAuthenticationService(PasswordEncoder bCryptPasswordEncoder, MovieEzUserService ignoredMovieEzUserService, MovieEzUserRepository movieEzUserService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.movieEzUserService = movieEzUserService;
    }

    public ResponseEntity<?> authenticateUserWithEmail(String email, String password) {
        return ResponseEntity.ok().build();
    }

}
