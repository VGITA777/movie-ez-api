package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzAppRole;
import com.prince.movieezapi.user.models.MovieEzUserRoleModel;
import com.prince.movieezapi.user.repository.MovieEzRolesRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieEzRolesService {

  private final MovieEzRolesRepository movieEzRolesRepository;

  public MovieEzRolesService(MovieEzRolesRepository movieEzRolesRepository) {
    this.movieEzRolesRepository = movieEzRolesRepository;
  }

  public MovieEzUserRoleModel save(MovieEzUserRoleModel role) {
    return movieEzRolesRepository.save(role);
  }

  public List<MovieEzUserRoleModel> getAll() {
    return movieEzRolesRepository.findAll();
  }

  public Optional<MovieEzUserRoleModel> getById(UUID id) {
    return movieEzRolesRepository.findById(id);
  }

  public Optional<MovieEzUserRoleModel> getByRole(MovieEzAppRole role) {
    return movieEzRolesRepository.findByDescription(role);
  }

  @Transactional
  public boolean deleteById(UUID id) {
    if (!movieEzRolesRepository.existsById(id)) {
      return false;
    }
    movieEzRolesRepository.deleteById(id);
    return true;
  }

  @Transactional
  public boolean deleteByRole(MovieEzAppRole role) {
    return movieEzRolesRepository.deleteByDescription(role) > 0;
  }
}
