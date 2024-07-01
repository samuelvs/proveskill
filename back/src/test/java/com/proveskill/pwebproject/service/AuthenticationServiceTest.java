package com.proveskill.pwebproject.service;

import com.proveskill.pwebproject.auth.AuthenticationRequest;
import com.proveskill.pwebproject.auth.AuthenticationResponse;
import com.proveskill.pwebproject.auth.ForgotPasswordRequest;
import com.proveskill.pwebproject.auth.RegisterRequest;
import com.proveskill.pwebproject.config.JwtService;
import com.proveskill.pwebproject.model.User;
import com.proveskill.pwebproject.repository.UserRepository;
import com.proveskill.pwebproject.user.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest registerRequest;
    private AuthenticationRequest authRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        registerRequest = RegisterRequest.builder().name("John Doe").email("john.doe@example.com")
                .school("School").role(Role.ADMIN).build();

        authRequest = new AuthenticationRequest("john.doe@example.com", "password");
    }

    @Test
    void testRegister() {
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);

        User savedUser = createUserFromRequest(registerRequest, encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        String mockToken = "mockToken";
        when(jwtService.generateToken(any(User.class))).thenReturn(mockToken);

        AuthenticationResponse response = authenticationService.register(registerRequest);

        Assertions.assertEquals(savedUser.getName(), response.getName());
        Assertions.assertEquals(savedUser.getEmail(), response.getEmail());
        Assertions.assertEquals(savedUser.getSchool(), response.getSchool());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticate() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenAnswer(invocation -> {
                    UsernamePasswordAuthenticationToken token = invocation.getArgument(0);
                    return new UsernamePasswordAuthenticationToken(token.getPrincipal(),
                            token.getCredentials(), new ArrayList<>());
                });
        User user = mockUserRepositoryFindByEmailSuccess();

        String mockToken = "mockToken";
        when(jwtService.generateToken(user)).thenReturn(mockToken);

        AuthenticationResponse response = authenticationService.authenticate(authRequest);

        Assertions.assertEquals(user.getName(), response.getName());
        Assertions.assertEquals(user.getEmail(), response.getEmail());
        Assertions.assertEquals(user.getSchool(), response.getSchool());

        verify(userRepository, times(1)).findByEmail(authRequest.getEmail());
    }

    @Test
    void testChangePassword() {
        AuthenticationRequest request =
                new AuthenticationRequest("john.doe@example.com", "newPassword");
        User user = mockUserRepositoryFindByEmailSuccess();
        String encodedNewPassword = "encodedNewPassword";
        when(passwordEncoder.encode(request.getPassword())).thenReturn(encodedNewPassword);

        AuthenticationRequest updatedRequest = authenticationService.changePassword(request);

        Assertions.assertEquals(request.getEmail(), updatedRequest.getEmail());
        Assertions.assertEquals(encodedNewPassword, user.getPassword());

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testForgotPassword() {
        ForgotPasswordRequest request = new ForgotPasswordRequest("john.doe@example.com");
        User user = mockUserRepositoryFindByEmailSuccess();
        String encodedNewPassword = "encodedNewPassword";
        when(passwordEncoder.encode(anyString())).thenReturn(encodedNewPassword);

        AuthenticationRequest updatedRequest = authenticationService.forgotPassword(request);

        Assertions.assertEquals(request.getEmail(), updatedRequest.getEmail());
        Assertions.assertEquals(encodedNewPassword, user.getPassword());

        verify(userRepository, times(1)).save(user);
    }

    private User createUserFromRequest(RegisterRequest request, String encodedPassword) {
        return User.builder().name(request.getName()).email(request.getEmail())
                .school(request.getSchool()).firstAccess(true).password(encodedPassword).build();
    }

    private User mockUserRepositoryFindByEmailSuccess() {
        User user = createUserFromRequest(registerRequest, "encodedPassword");
        when(userRepository.findByEmail(authRequest.getEmail())).thenReturn(Optional.of(user));
        return user;
    }
}
