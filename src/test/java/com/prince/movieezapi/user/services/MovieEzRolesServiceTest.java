package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzAppRole;
import com.prince.movieezapi.user.models.MovieEzUserRoleModel;
import com.prince.movieezapi.user.repository.MovieEzRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieEzRolesServiceTest {

    @Mock
    private MovieEzRolesRepository movieEzRolesRepository;

    @InjectMocks
    private MovieEzRolesService movieEzRolesService;

    private MovieEzUserRoleModel testRole;
    private UUID testId;

    @BeforeEach
    void setUp() {
        testId = UUID.randomUUID();
        testRole = new MovieEzUserRoleModel();
        testRole.setId(testId);
        testRole.setDescription(MovieEzAppRole.USER);
    }

    @Test
    void save_ShouldReturnSavedRole() {
        // Arrange
        when(movieEzRolesRepository.save(any(MovieEzUserRoleModel.class))).thenReturn(testRole);

        // Act
        MovieEzUserRoleModel result = movieEzRolesService.save(testRole);

        // Assert
        assertNotNull(result);
        assertEquals(testId, result.getId());
        assertEquals(MovieEzAppRole.USER, result.getDescription());
        verify(movieEzRolesRepository, times(1)).save(testRole);
    }

    @Test
    void getAll_ShouldReturnAllRoles() {
        // Arrange
        List<MovieEzUserRoleModel> roles = Arrays.asList(testRole, new MovieEzUserRoleModel());
        when(movieEzRolesRepository.findAll()).thenReturn(roles);

        // Act
        List<MovieEzUserRoleModel> result = movieEzRolesService.getAll();

        // Assert
        assertEquals(2, result.size());
        verify(movieEzRolesRepository, times(1)).findAll();
    }

    @Test
    void getById_WhenExists_ShouldReturnRole() {
        // Arrange
        when(movieEzRolesRepository.findById(testId)).thenReturn(Optional.of(testRole));

        // Act
        Optional<MovieEzUserRoleModel> result = movieEzRolesService.getById(testId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testId, result.get().getId());
        verify(movieEzRolesRepository, times(1)).findById(testId);
    }

    @Test
    void getById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(movieEzRolesRepository.findById(testId)).thenReturn(Optional.empty());

        // Act
        Optional<MovieEzUserRoleModel> result = movieEzRolesService.getById(testId);

        // Assert
        assertFalse(result.isPresent());
        verify(movieEzRolesRepository, times(1)).findById(testId);
    }

    @Test
    void getByRole_ShouldReturnRoleByDescription() {
        // Arrange
        when(movieEzRolesRepository.findByDescription(MovieEzAppRole.USER))
                .thenReturn(Optional.of(testRole));

        // Act
        Optional<MovieEzUserRoleModel> result = movieEzRolesService.getByRole(MovieEzAppRole.USER);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(MovieEzAppRole.USER, result.get().getDescription());
        verify(movieEzRolesRepository, times(1)).findByDescription(MovieEzAppRole.USER);
    }

    @Test
    void deleteById_WhenExists_ShouldReturnTrue() {
        // Arrange
        when(movieEzRolesRepository.existsById(testId)).thenReturn(true);
        doNothing().when(movieEzRolesRepository).deleteById(testId);

        // Act
        boolean result = movieEzRolesService.deleteById(testId);

        // Assert
        assertTrue(result);
        verify(movieEzRolesRepository, times(1)).existsById(testId);
        verify(movieEzRolesRepository, times(1)).deleteById(testId);
    }

    @Test
    void deleteById_WhenNotExists_ShouldReturnFalse() {
        // Arrange
        when(movieEzRolesRepository.existsById(testId)).thenReturn(false);

        // Act
        boolean result = movieEzRolesService.deleteById(testId);

        // Assert
        assertFalse(result);
        verify(movieEzRolesRepository, times(1)).existsById(testId);
        verify(movieEzRolesRepository, never()).deleteById(any());
    }

    @Test
    void deleteByRole_WhenExists_ShouldReturnTrue() {
        // Arrange
        when(movieEzRolesRepository.deleteByDescription(MovieEzAppRole.USER)).thenReturn(1);

        // Act
        boolean result = movieEzRolesService.deleteByRole(MovieEzAppRole.USER);

        // Assert
        assertTrue(result);
        verify(movieEzRolesRepository, times(1)).deleteByDescription(MovieEzAppRole.USER);
    }

    @Test
    void deleteByRole_WhenNotExists_ShouldReturnFalse() {
        // Arrange
        when(movieEzRolesRepository.deleteByDescription(MovieEzAppRole.USER)).thenReturn(0);

        // Act
        boolean result = movieEzRolesService.deleteByRole(MovieEzAppRole.USER);

        // Assert
        assertFalse(result);
        verify(movieEzRolesRepository, times(1)).deleteByDescription(MovieEzAppRole.USER);
    }
}
