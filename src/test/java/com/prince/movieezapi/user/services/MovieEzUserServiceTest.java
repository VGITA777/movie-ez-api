package com.prince.movieezapi.user.services;

import com.prince.movieezapi.security.services.UserSessionService;
import com.prince.movieezapi.user.exceptions.UserNotFoundException;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.repository.MovieEzUserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieEzUserServiceTest {

    @Mock
    private MovieEzUserRepository movieEzUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserSessionService userSessionService;

    @Mock
    private HttpSession httpSession;

    @InjectMocks
    private MovieEzUserService movieEzUserService;

    private MovieEzUserModel testUser;
    private UUID testUserId;

    @BeforeEach
    void setUp() {
        testUserId = UUID.randomUUID();
        testUser = new MovieEzUserModel();
        testUser.setId(testUserId);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("encodedPassword123");
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        when(movieEzUserRepository.findByEmail(email)).thenReturn(Optional.of(testUser));

        // Act
        Optional<MovieEzUserModel> result = movieEzUserService.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        verify(movieEzUserRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByEmail_WhenUserNotExists_ShouldReturnEmpty() {
        // Arrange
        String email = "nonexistent@example.com";
        when(movieEzUserRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<MovieEzUserModel> result = movieEzUserService.findByEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(movieEzUserRepository, times(1)).findByEmail(email);
    }

    @Test
    void findByUsername_WhenUserExists_ShouldReturnUser() {
        // Arrange
        String username = "testuser";
        when(movieEzUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        // Act
        Optional<MovieEzUserModel> result = movieEzUserService.findByUsername(username);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(username, result.get().getUsername());
        verify(movieEzUserRepository, times(1)).findByUsername(username);
    }

    @Test
    void findByUsername_WhenUserNotExists_ShouldReturnEmpty() {
        // Arrange
        String username = "nonexistent";
        when(movieEzUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        Optional<MovieEzUserModel> result = movieEzUserService.findByUsername(username);

        // Assert
        assertFalse(result.isPresent());
        verify(movieEzUserRepository, times(1)).findByUsername(username);
    }

    @Test
    void findById_WhenUserExists_ShouldReturnUser() {
        // Arrange
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));

        // Act
        Optional<MovieEzUserModel> result = movieEzUserService.findById(testUserId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testUserId, result.get().getId());
        verify(movieEzUserRepository, times(1)).findById(testUserId);
    }

    @Test
    void save_ShouldEncodePasswordAndSaveUser() {
        // Arrange
        MovieEzUserModel newUser = new MovieEzUserModel();
        newUser.setUsername("newuser");
        newUser.setEmail("new@example.com");
        newUser.setPassword("plainPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(movieEzUserRepository.save(any(MovieEzUserModel.class))).thenReturn(newUser);

        // Act
        MovieEzUserModel result = movieEzUserService.save(newUser);

        // Assert
        assertNotNull(result);
        assertEquals("encodedPassword", newUser.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(movieEzUserRepository, times(1)).save(newUser);
    }

    @Test
    void delete_WithValidPassword_ShouldDeleteUserAndSessions() {
        // Arrange
        String password = "correctPassword";
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(password, testUser.getPassword())).thenReturn(true);
        doNothing().when(movieEzUserRepository).delete(testUser);
        doNothing().when(userSessionService).deleteAllSessionsByPrincipalName(testUserId);

        // Act
        movieEzUserService.delete(testUserId, password);

        // Assert
        verify(movieEzUserRepository, times(1)).findById(testUserId);
        verify(passwordEncoder, times(1)).matches(password, testUser.getPassword());
        verify(movieEzUserRepository, times(1)).delete(testUser);
        verify(userSessionService, times(1)).deleteAllSessionsByPrincipalName(testUserId);
    }

    @Test
    void delete_WhenUserNotFound_ShouldThrowUserNotFoundException() {
        // Arrange
        UUID nonExistentId = UUID.randomUUID();
        when(movieEzUserRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                movieEzUserService.delete(nonExistentId, "password")
        );

        assertTrue(exception.getMessage().contains("does not exists"));
        verify(movieEzUserRepository, times(1)).findById(nonExistentId);
        verify(movieEzUserRepository, never()).delete(any());
        verify(userSessionService, never()).deleteAllSessionsByPrincipalName(any());
    }

    @Test
    void delete_WithIncorrectPassword_ShouldThrowBadCredentialsException() {
        // Arrange
        String wrongPassword = "wrongPassword";
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(wrongPassword, testUser.getPassword())).thenReturn(false);

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () ->
                movieEzUserService.delete(testUserId, wrongPassword)
        );

        assertTrue(exception.getMessage().contains("password does not match"));
        verify(movieEzUserRepository, times(1)).findById(testUserId);
        verify(passwordEncoder, times(1)).matches(wrongPassword, testUser.getPassword());
        verify(movieEzUserRepository, never()).delete(any());
        verify(userSessionService, never()).deleteAllSessionsByPrincipalName(any());
    }

    @Test
    void existsByEmail_WhenExists_ShouldReturnTrue() {
        // Arrange
        String email = "test@example.com";
        when(movieEzUserRepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean result = movieEzUserService.existsByEmail(email);

        // Assert
        assertTrue(result);
        verify(movieEzUserRepository, times(1)).existsByEmail(email);
    }

    @Test
    void existsByEmail_WhenNotExists_ShouldReturnFalse() {
        // Arrange
        String email = "nonexistent@example.com";
        when(movieEzUserRepository.existsByEmail(email)).thenReturn(false);

        // Act
        boolean result = movieEzUserService.existsByEmail(email);

        // Assert
        assertFalse(result);
        verify(movieEzUserRepository, times(1)).existsByEmail(email);
    }

    @Test
    void existsByUsername_WhenExists_ShouldReturnTrue() {
        // Arrange
        String username = "testuser";
        when(movieEzUserRepository.existsByUsername(username)).thenReturn(true);

        // Act
        boolean result = movieEzUserService.existsByUsername(username);

        // Assert
        assertTrue(result);
        verify(movieEzUserRepository, times(1)).existsByUsername(username);
    }

    @Test
    void existsByUsername_WhenNotExists_ShouldReturnFalse() {
        // Arrange
        String username = "nonexistent";
        when(movieEzUserRepository.existsByUsername(username)).thenReturn(false);

        // Act
        boolean result = movieEzUserService.existsByUsername(username);

        // Assert
        assertFalse(result);
        verify(movieEzUserRepository, times(1)).existsByUsername(username);
    }

    @Test
    void updatePasswordById_WithValidData_ShouldUpdatePasswordAndInvalidateSessions() {
        // Arrange
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String encodedNewPassword = "encodedNewPassword";
        String sessionId = "session123";

        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(oldPassword, testUser.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, testUser.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedNewPassword);
        when(movieEzUserRepository.save(testUser)).thenReturn(testUser);
        when(httpSession.getId()).thenReturn(sessionId);

        // Act
        MovieEzUserModel result = movieEzUserService.updatePasswordById(
                testUserId, oldPassword, newPassword, httpSession, true, false
        );

        // Assert
        assertNotNull(result);
        assertEquals(encodedNewPassword, testUser.getPassword());
        verify(movieEzUserRepository, times(1)).findById(testUserId);
        verify(passwordEncoder, times(1)).matches(newPassword, "encodedPassword123"); // Original password
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(movieEzUserRepository, times(1)).save(testUser);
        verify(userSessionService, times(1)).deleteAllSessionsByPrincipalNameExcludeSessionId(testUserId, sessionId);
        verify(userSessionService, never()).deleteAllSessionsByPrincipalName(any());
    }

    @Test
    void updatePasswordById_InvalidateAllSessions_ShouldDeleteAllSessions() {
        // Arrange
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(oldPassword, testUser.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, testUser.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        when(movieEzUserRepository.save(testUser)).thenReturn(testUser);

        // Act
        movieEzUserService.updatePasswordById(
                testUserId, oldPassword, newPassword, httpSession, true, true
        );

        // Assert
        verify(userSessionService, times(1)).deleteAllSessionsByPrincipalName(testUserId);
        verify(userSessionService, never()).deleteAllSessionsByPrincipalNameExcludeSessionId(any(), anyString());
    }

    @Test
    void updatePasswordById_NoSessionInvalidation_ShouldNotDeleteSessions() {
        // Arrange
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(oldPassword, testUser.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(newPassword, testUser.getPassword())).thenReturn(false);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");
        when(movieEzUserRepository.save(testUser)).thenReturn(testUser);

        // Act
        movieEzUserService.updatePasswordById(
                testUserId, oldPassword, newPassword, httpSession, false, false
        );

        // Assert
        verify(userSessionService, never()).deleteAllSessionsByPrincipalName(any());
        verify(userSessionService, never()).deleteAllSessionsByPrincipalNameExcludeSessionId(any(), anyString());
    }

    @Test
    void updatePasswordById_WhenUserNotFound_ShouldThrowUserNotFoundException() {
        // Arrange
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                movieEzUserService.updatePasswordById(testUserId, "old", "new", httpSession, false, false)
        );

        assertTrue(exception.getMessage().contains("User not found"));
        verify(movieEzUserRepository, times(1)).findById(testUserId);
        verify(movieEzUserRepository, never()).save(any());
    }

    @Test
    void updatePasswordById_WithIncorrectOldPassword_ShouldThrowBadCredentialsException() {
        // Arrange
        String wrongOldPassword = "wrongPassword";
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(wrongOldPassword, testUser.getPassword())).thenReturn(false);

        // Act & Assert
        BadCredentialsException exception = assertThrows(BadCredentialsException.class, () ->
                movieEzUserService.updatePasswordById(testUserId, wrongOldPassword, "newPassword", httpSession, false, false)
        );

        assertTrue(exception.getMessage().contains("Old password does not match"));
        verify(movieEzUserRepository, never()).save(any());
    }

    @Test
    void updatePasswordById_WhenNewPasswordSameAsOld_ShouldThrowIllegalArgumentException() {
        // Arrange
        String samePassword = "samePassword";
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(samePassword, testUser.getPassword())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                movieEzUserService.updatePasswordById(testUserId, samePassword, samePassword, httpSession, false, false)
        );

        assertTrue(exception.getMessage().contains("New password cannot be the same as old password"));
        verify(movieEzUserRepository, never()).save(any());
    }

    @Test
    void updateUsernameById_WithValidData_ShouldUpdateUsername() {
        // Arrange
        String newUsername = "newUsername";
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.of(testUser));
        when(movieEzUserRepository.save(testUser)).thenReturn(testUser);

        // Act
        MovieEzUserModel result = movieEzUserService.updateUsernameById(testUserId, newUsername);

        // Assert
        assertNotNull(result);
        assertEquals(newUsername, testUser.getUsername());
        verify(movieEzUserRepository, times(1)).findById(testUserId);
        verify(movieEzUserRepository, times(1)).save(testUser);
    }

    @Test
    void updateUsernameById_WhenUserNotFound_ShouldThrowUserNotFoundException() {
        // Arrange
        String newUsername = "newUsername";
        when(movieEzUserRepository.findById(testUserId)).thenReturn(Optional.empty());

        // Act & Assert
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () ->
                movieEzUserService.updateUsernameById(testUserId, newUsername)
        );

        assertTrue(exception.getMessage().contains("User not found"));
        verify(movieEzUserRepository, times(1)).findById(testUserId);
        verify(movieEzUserRepository, never()).save(any());
    }
}