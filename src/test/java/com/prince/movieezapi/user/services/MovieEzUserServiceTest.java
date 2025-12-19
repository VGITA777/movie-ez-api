package com.prince.movieezapi.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.prince.movieezapi.security.services.UserSessionService;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class MovieEzUserServiceTest {

  @Mock
  MovieEzUserRepository movieEzUserRepository;

  @Mock
  PasswordEncoder passwordEncoder;

  @Mock
  UserSessionService userSessionService;

  @Mock
  HttpSession httpSession;

  @InjectMocks
  MovieEzUserService service;

  @Captor
  ArgumentCaptor<MovieEzUserModel> userModelCaptor;

  @Test
  void findByEmail_delegatesToRepository() {
    String email = "a@b.com";
    MovieEzUserModel user = new MovieEzUserModel();
    user.setEmail(email);

    when(movieEzUserRepository.findByEmail(email)).thenReturn(Optional.of(user));

    Optional<MovieEzUserModel> result = service.findByEmail(email);

    assertTrue(result.isPresent());
    assertEquals(email, result.get().getEmail());
    verify(movieEzUserRepository).findByEmail(email);
  }

  @Test
  void findByUsername_delegatesToRepository() {
    String username = "tester";
    MovieEzUserModel user = new MovieEzUserModel();
    user.setUsername(username);

    when(movieEzUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

    Optional<MovieEzUserModel> result = service.findByUsername(username);

    assertTrue(result.isPresent());
    assertEquals(username, result.get().getUsername());
    verify(movieEzUserRepository).findByUsername(username);
  }

  @Test
  void findById_delegatesToRepository() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));

    Optional<MovieEzUserModel> result = service.findById(id);

    assertTrue(result.isPresent());
    assertEquals(id, result.get().getId());
    verify(movieEzUserRepository).findById(id);
  }

  @Test
  void save_encodesPasswordAndSaves() {
    MovieEzUserModel incoming = new MovieEzUserModel();
    incoming.setPassword("rawPassword");

    when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");
    when(movieEzUserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    MovieEzUserModel saved = service.save(incoming);

    // verify repository.save got a model whose password was encoded
    verify(movieEzUserRepository).save(userModelCaptor.capture());
    MovieEzUserModel captured = userModelCaptor.getValue();
    assertEquals("encodedPassword", captured.getPassword());
    // returned value should be the saved model (we used answer to return the argument)
    assertEquals("encodedPassword", saved.getPassword());
    verify(passwordEncoder).encode("rawPassword");
  }

  @Test
  void delete_success_deletesAndInvalidatesSessions() {
    UUID id = UUID.randomUUID();
    String rawPassword = "raw";
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setPassword("encoded"); // stored password

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(rawPassword, "encoded")).thenReturn(true);

    service.delete(id, rawPassword);

    verify(movieEzUserRepository).delete(user);
    verify(userSessionService).deleteAllSessionsByPrincipalName(id);
  }

  @Test
  void delete_userNotFound_throws() {
    UUID id = UUID.randomUUID();
    when(movieEzUserRepository.findById(id)).thenReturn(Optional.empty());

    UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> service.delete(id, "any"));
    assertTrue(ex.getMessage().contains(id.toString()));

    verify(movieEzUserRepository, never()).delete(any());
    verifyNoInteractions(userSessionService);
  }

  @Test
  void delete_badPassword_throwsBadCredentials() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setPassword("encoded");

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

    assertThrows(BadCredentialsException.class, () -> service.delete(id, "wrong"));

    verify(movieEzUserRepository, never()).delete(any());
    verifyNoInteractions(userSessionService);
  }

  @Test
  void existsByEmail_delegatesToRepository() {
    when(movieEzUserRepository.existsByEmail("a@b.com")).thenReturn(true);

    assertTrue(service.existsByEmail("a@b.com"));
    verify(movieEzUserRepository).existsByEmail("a@b.com");
  }

  @Test
  void existsByUsername_delegatesToRepository() {
    when(movieEzUserRepository.existsByUsername("u")).thenReturn(false);

    assertFalse(service.existsByUsername("u"));
    verify(movieEzUserRepository).existsByUsername("u");
  }

  @Test
  void updatePasswordById_success_invalidateAllSessions() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setPassword("oldEncoded");

    String oldPassword = "oldRaw";
    String newPassword = "newRaw";
    String newEncoded = "newEncodedValue";

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(oldPassword, "oldEncoded")).thenReturn(true);
    when(passwordEncoder.matches(newPassword, "oldEncoded")).thenReturn(false);
    when(passwordEncoder.encode(newPassword)).thenReturn(newEncoded);
    when(movieEzUserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    MovieEzUserModel result = service.updatePasswordById(id, oldPassword, newPassword, httpSession, true, true);

    // verify password updated and saved
    verify(movieEzUserRepository).save(userModelCaptor.capture());
    assertEquals(newEncoded, userModelCaptor.getValue().getPassword());
    assertEquals(newEncoded, result.getPassword());

    // verify all sessions invalidated
    verify(userSessionService).deleteAllSessionsByPrincipalName(id);
    verify(userSessionService, never()).deleteAllSessionsByPrincipalNameExcludeSessionId(any(), any());
  }

  @Test
  void updatePasswordById_success_invalidateExcludeSession() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setPassword("oldEncoded");

    String oldPassword = "oldRaw";
    String newPassword = "newRaw";
    String newEncoded = "newEncodedValue";

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(oldPassword, "oldEncoded")).thenReturn(true);
    when(passwordEncoder.matches(newPassword, "oldEncoded")).thenReturn(false);
    when(passwordEncoder.encode(newPassword)).thenReturn(newEncoded);
    when(movieEzUserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
    when(httpSession.getId()).thenReturn("SESSION123");

    MovieEzUserModel result = service.updatePasswordById(id, oldPassword, newPassword, httpSession, true, false);

    verify(movieEzUserRepository).save(userModelCaptor.capture());
    assertEquals(newEncoded, userModelCaptor.getValue().getPassword());
    assertEquals(newEncoded, result.getPassword());

    verify(userSessionService).deleteAllSessionsByPrincipalNameExcludeSessionId(id, "SESSION123");
    verify(userSessionService, never()).deleteAllSessionsByPrincipalName(any());
  }

  @Test
  void updatePasswordById_oldPasswordMismatch_throws() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setPassword("encoded");

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches("wrongOld", "encoded")).thenReturn(false);

    assertThrows(
        BadCredentialsException.class,
        () -> service.updatePasswordById(id, "wrongOld", "new", httpSession, false, false)
    );

    verify(movieEzUserRepository, never()).save(any());
    verifyNoInteractions(userSessionService);
  }

  @Test
  void updatePasswordById_newPasswordSameAsOld_throwsIllegalArgument() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setPassword("encodedOld");

    String oldPassword = "oldRaw";
    String newPassword = "newRaw";

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(passwordEncoder.matches(oldPassword, "encodedOld")).thenReturn(true);
    // Simulate new password equals old by having matches(newPassword, userPassword) return true
    when(passwordEncoder.matches(newPassword, "encodedOld")).thenReturn(true);

    IllegalArgumentException ex = assertThrows(
        IllegalArgumentException.class,
        () -> service.updatePasswordById(id, oldPassword, newPassword, httpSession, false, false)
    );

    assertTrue(ex.getMessage().toLowerCase().contains("new password cannot be the same"));
    verify(movieEzUserRepository, never()).save(any());
    verifyNoInteractions(userSessionService);
  }

  @Test
  void updateUsernameById_success() {
    UUID id = UUID.randomUUID();
    MovieEzUserModel user = new MovieEzUserModel();
    user.setId(id);
    user.setUsername("oldName");

    when(movieEzUserRepository.findById(id)).thenReturn(Optional.of(user));
    when(movieEzUserRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    MovieEzUserModel result = service.updateUsernameById(id, "newName");

    verify(movieEzUserRepository).save(userModelCaptor.capture());
    assertEquals("newName", userModelCaptor.getValue().getUsername());
    assertEquals("newName", result.getUsername());
  }

  @Test
  void updateUsernameById_notFound_throwsUserNotFound() {
    UUID id = UUID.randomUUID();
    when(movieEzUserRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> service.updateUsernameById(id, "whatever"));

    verify(movieEzUserRepository, never()).save(any());
  }
}
