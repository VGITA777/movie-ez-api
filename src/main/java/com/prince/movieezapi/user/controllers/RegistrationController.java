package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserDtoMapper;
import com.prince.movieezapi.user.inputs.UserRegistrationInput;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.services.MovieEzUserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegistrationController {

  private final MovieEzUserService movieEzUserService;
  private final MovieEzUserDtoMapper movieEzUserDtoMapper;

  public RegistrationController(MovieEzUserService movieEzUserService, MovieEzUserDtoMapper movieEzUserDtoMapper) {
    this.movieEzUserService = movieEzUserService;
    this.movieEzUserDtoMapper = movieEzUserDtoMapper;
  }

  @RateLimiter(name = "userRegisterEndpoint")
  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationInput input) {
    MovieEzUserModel newUser = MovieEzUserModel
        .builder()
        .username(input.username())
        .email(input.email())
        .password(input.password())
        .build();
    MovieEzUserModel saved = movieEzUserService.save(newUser);
    return ResponseEntity.ok(ServerGenericResponse.success(
        "User registration endpoint",
        movieEzUserDtoMapper.toDto(saved)
    ));
  }
}
