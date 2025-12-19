package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzAppRole;
import com.prince.movieezapi.user.models.MovieEzUserRoleModel;
import com.prince.movieezapi.user.repository.MovieEzRolesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) class MovieEzRolesServiceTest {

    @Mock
    MovieEzRolesRepository movieEzRolesRepository;

    @InjectMocks
    MovieEzRolesService service;

    @Captor
    ArgumentCaptor<MovieEzUserRoleModel> roleCaptor;

    @Test
    void save_delegatesToRepository_andReturnsSaved() {
        MovieEzUserRoleModel incoming = new MovieEzUserRoleModel();
        incoming.setDescription(MovieEzAppRole.USER);

        MovieEzUserRoleModel saved = new MovieEzUserRoleModel();
        saved.setId(UUID.randomUUID());
        saved.setDescription(MovieEzAppRole.USER);

        when(movieEzRolesRepository.save(incoming)).thenReturn(saved);

        MovieEzUserRoleModel result = service.save(incoming);

        assertNotNull(result);
        assertEquals(saved.getId(), result.getId());
        assertEquals(MovieEzAppRole.USER, result.getDescription());
        verify(movieEzRolesRepository).save(incoming);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void getAll_returnsListFromRepository() {
        MovieEzUserRoleModel r1 = new MovieEzUserRoleModel();
        r1.setId(UUID.randomUUID());
        r1.setDescription(MovieEzAppRole.ADMIN);

        MovieEzUserRoleModel r2 = new MovieEzUserRoleModel();
        r2.setId(UUID.randomUUID());
        r2.setDescription(MovieEzAppRole.USER);

        List<MovieEzUserRoleModel> repoList = Arrays.asList(r1, r2);
        when(movieEzRolesRepository.findAll()).thenReturn(repoList);

        List<MovieEzUserRoleModel> result = service.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        verify(movieEzRolesRepository).findAll();
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void getById_returnsOptionalFromRepository_whenPresent() {
        UUID id = UUID.randomUUID();
        MovieEzUserRoleModel role = new MovieEzUserRoleModel();
        role.setId(id);
        role.setDescription(MovieEzAppRole.USER);

        when(movieEzRolesRepository.findById(id)).thenReturn(Optional.of(role));

        Optional<MovieEzUserRoleModel> opt = service.getById(id);

        assertTrue(opt.isPresent());
        assertEquals(id, opt.get().getId());
        verify(movieEzRolesRepository).findById(id);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void getById_returnsEmptyOptional_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(movieEzRolesRepository.findById(id)).thenReturn(Optional.empty());

        Optional<MovieEzUserRoleModel> opt = service.getById(id);

        assertFalse(opt.isPresent());
        verify(movieEzRolesRepository).findById(id);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void getByRole_returnsOptionalFromRepository_whenPresent() {
        MovieEzAppRole appRole = MovieEzAppRole.ADMIN;
        MovieEzUserRoleModel role = new MovieEzUserRoleModel();
        role.setId(UUID.randomUUID());
        role.setDescription(appRole);

        when(movieEzRolesRepository.findByDescription(appRole)).thenReturn(Optional.of(role));

        Optional<MovieEzUserRoleModel> opt = service.getByRole(appRole);

        assertTrue(opt.isPresent());
        assertEquals(appRole, opt.get().getDescription());
        verify(movieEzRolesRepository).findByDescription(appRole);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void getByRole_returnsEmptyOptional_whenNotFound() {
        MovieEzAppRole appRole = MovieEzAppRole.USER;
        when(movieEzRolesRepository.findByDescription(appRole)).thenReturn(Optional.empty());

        Optional<MovieEzUserRoleModel> opt = service.getByRole(appRole);

        assertFalse(opt.isPresent());
        verify(movieEzRolesRepository).findByDescription(appRole);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void deleteById_returnsFalse_whenIdDoesNotExist_andDoesNotDelete() {
        UUID id = UUID.randomUUID();
        when(movieEzRolesRepository.existsById(id)).thenReturn(false);

        boolean result = service.deleteById(id);

        assertFalse(result);
        verify(movieEzRolesRepository).existsById(id);
        verify(movieEzRolesRepository, never()).deleteById(any());
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void deleteById_returnsTrue_whenIdExists_andDeletes() {
        UUID id = UUID.randomUUID();
        when(movieEzRolesRepository.existsById(id)).thenReturn(true);
        // repository.deleteById returns void; we just verify it is called
        doNothing().when(movieEzRolesRepository).deleteById(id);

        boolean result = service.deleteById(id);

        assertTrue(result);
        verify(movieEzRolesRepository).existsById(id);
        verify(movieEzRolesRepository).deleteById(id);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void deleteByRole_returnsTrue_whenRepositoryDeletesAtLeastOne() {
        MovieEzAppRole role = MovieEzAppRole.USER;
        when(movieEzRolesRepository.deleteByDescription(role)).thenReturn(2); // > 0 means deleted
        boolean result = service.deleteByRole(role);

        assertTrue(result);
        verify(movieEzRolesRepository).deleteByDescription(role);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }

    @Test
    void deleteByRole_returnsFalse_whenRepositoryDeletesZero() {
        MovieEzAppRole role = MovieEzAppRole.ADMIN;
        when(movieEzRolesRepository.deleteByDescription(role)).thenReturn(0); // nothing deleted
        boolean result = service.deleteByRole(role);

        assertFalse(result);
        verify(movieEzRolesRepository).deleteByDescription(role);
        verifyNoMoreInteractions(movieEzRolesRepository);
    }
}
