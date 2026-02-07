package com.prince.movieezapi.security.filters;

import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class UserInformationSyncFilter extends OncePerRequestFilter {

  private final MovieEzUserRepository movieEzUserRepository;
  private final SecurityContext securityContext = SecurityContextHolder
      .getContextHolderStrategy()
      .getContext();

  public UserInformationSyncFilter(MovieEzUserRepository movieEzUserRepository) {
    this.movieEzUserRepository = movieEzUserRepository;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var authentication = securityContext.getAuthentication();

    if (!(authentication instanceof JwtAuthenticationToken token) || token.getPrincipal() == null) {
      filterChain.doFilter(request, response);
      return;
    }

    var jwt = Jwt
        .withTokenValue(token
                            .getPrincipal()
                            .toString())
        .build();
    var userId = UUID.fromString(jwt.getSubject());
    var optionalUser = movieEzUserRepository.findById(userId);

    if (optionalUser.isEmpty()) {
      filterChain.doFilter(request, response);
      log.info("User with ID '{}' not found in database, creating new user to the database", userId);
      handleNewUser(jwt);
      log.info("User with ID '{}' successfully created in database", userId);
      return;
    }

    var user = optionalUser.get();
    var emailClaim = jwt.getClaimAsString("email");
    var usernameClaim = jwt.getClaimAsString("preferred_username");

    user.setEmail(emailClaim);
    user.setUsername(usernameClaim);
    movieEzUserRepository.save(user);

    log.info("User with ID '{}' successfully synced with database", userId);

    filterChain.doFilter(request, response);
  }

  private void handleNewUser(Jwt jwt) {
    var movieEzUserModel = createMovieEzUserModel(jwt);
    movieEzUserRepository.save(movieEzUserModel);
  }

  private MovieEzUserModel createMovieEzUserModel(Jwt jwt) {
    return MovieEzUserModel
        .builder()
        .id(UUID.fromString(jwt.getSubject()))
        .email(jwt.getClaimAsString("email"))
        .username(jwt.getClaimAsString("preferred_username"))
        .build();
  }

}
