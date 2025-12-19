package com.prince.movieezapi.user.services;

import com.prince.movieezapi.security.authenticationtokens.MovieEzEmailPasswordAuthenticationToken;
import com.prince.movieezapi.security.authenticationtokens.MovieEzUsernamePasswordAuthenticationToken;
import com.prince.movieezapi.shared.utilities.SecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) class UserEmailAndUsernameAuthenticationServiceTest {

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    HttpSessionSecurityContextRepository securityContextRepository;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    Authentication authenticated;

    @InjectMocks
    UserEmailAndUsernameAuthenticationService service;

    @Captor
    ArgumentCaptor<Authentication> authenticationCaptor;

    @Test
    void authenticateUserWithEmail_success_invokesSecurityUtils() {
        // Arrange
        String email = "foo@example.com";
        String password = "s3cr3t";

        when(authenticationManager.authenticate(authenticationCaptor.capture())).thenReturn(authenticated);

        // Act + Assert static mocking of SecurityUtils
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            service.authenticateUserWithEmail(email, password, request, response);

            // Verify that the expected token type was passed to AuthenticationManager
            Authentication passed = authenticationCaptor.getValue();
            assertNotNull(passed, "Authentication passed to manager should not be null");
            assertTrue(passed instanceof MovieEzEmailPasswordAuthenticationToken,
                       "Expected MovieEzEmailPasswordAuthenticationToken to be used");

            // Check principal and credentials are what we supplied (token contracts usually put them here)
            assertEquals(email, passed.getPrincipal(), "Principal should be the email supplied");
            assertEquals(password, passed.getCredentials(), "Credentials should be the password supplied");

            // Verify that SecurityUtils.setCurrentAuthentication(...) was called with the authenticated object
            securityUtilsMock.verify(() -> SecurityUtils.setCurrentAuthentication(authenticated,
                                                                                  securityContextRepository,
                                                                                  request,
                                                                                  response));
        }
    }

    @Test
    void authenticateUserWithEmail_failure_doesNotInvokeSecurityUtils() {
        // Arrange
        String email = "bad@example.com";
        String password = "badpass";

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("bad credentials"));

        // Act + Assert
        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            BadCredentialsException ex = assertThrows(BadCredentialsException.class,
                                                      () -> service.authenticateUserWithEmail(email,
                                                                                              password,
                                                                                              request,
                                                                                              response));

            assertEquals("bad credentials", ex.getMessage());

            // Ensure the static helper was never invoked (and repository wasn't interacted with)
            // We verify repository has zero interactions which is a good proxy here.
            verifyNoInteractions(securityContextRepository);
            // Also assert SecurityUtils.setCurrentAuthentication was not called
            securityUtilsMock.verifyNoInteractions();
        }
    }

    @Test
    void authenticateUserWithUsername_success_invokesSecurityUtils() {
        // Arrange
        String username = "theuser";
        String password = "pass123";

        when(authenticationManager.authenticate(authenticationCaptor.capture())).thenReturn(authenticated);

        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Act
            service.authenticateUserWithUsername(username, password, request, response);

            // Verify token type and contents
            Authentication passed = authenticationCaptor.getValue();
            assertNotNull(passed);
            assertInstanceOf(MovieEzUsernamePasswordAuthenticationToken.class,
                             passed,
                             "Expected MovieEzUsernamePasswordAuthenticationToken to be used");
            assertEquals(username, passed.getPrincipal());
            assertEquals(password, passed.getCredentials());

            // Verify static call
            securityUtilsMock.verify(() -> SecurityUtils.setCurrentAuthentication(authenticated,
                                                                                  securityContextRepository,
                                                                                  request,
                                                                                  response));
        }
    }

    @Test
    void authenticateUserWithUsername_failure_doesNotInvokeSecurityUtils() {
        // Arrange
        String username = "baduser";
        String password = "nopass";

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("invalid"));

        try (MockedStatic<SecurityUtils> securityUtilsMock = mockStatic(SecurityUtils.class)) {
            // Act + Assert
            assertThrows(BadCredentialsException.class,
                         () -> service.authenticateUserWithUsername(username, password, request, response));

            // Ensure repository/static helper not used
            verifyNoInteractions(securityContextRepository);
            securityUtilsMock.verifyNoInteractions();
        }
    }
}
