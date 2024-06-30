package com.proveskill.pwebproject.service;

import com.proveskill.pwebproject.auth.AuthenticationRequest;
import com.proveskill.pwebproject.auth.AuthenticationResponse;
import com.proveskill.pwebproject.auth.ForgotPasswordRequest;
import com.proveskill.pwebproject.auth.RegisterRequest;
import com.proveskill.pwebproject.config.JwtService;
import com.proveskill.pwebproject.model.User;
import com.proveskill.pwebproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    String password = new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char) i))
        .collect(Collectors.joining());
    var user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .school(request.getSchool())
        .firstAccess(true)
        .password(passwordEncoder.encode(password))
        .role(request.getRole())
        .build();

    repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .name(user.getName())
        .email(user.getEmail())
        .password(password)
        .school(user.getSchool())
        .firstAccess(user.getFirstAcess())
        .role(user.getRole())
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword(),
            new ArrayList<>()));

    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();

    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .name(user.getName())
        .email(user.getEmail())
        .school(user.getSchool())
        .firstAccess(user.getFirstAcess())
        .role(user.getRole())
        .build();
  }

  public AuthenticationRequest changePassword(AuthenticationRequest request) {
    var user = repository.findByEmail(request.getEmail()).orElseThrow();
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setFirstAccess(false);

    repository.save(user);
    return AuthenticationRequest.builder()
        .email(user.getEmail())
        .password(request.getPassword())
        .build();
  }

  public AuthenticationRequest forgotPassword(ForgotPasswordRequest request) {
    var user = repository.findByEmail(request.getEmail()).orElseThrow();

    String password = new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char) i))
        .collect(Collectors.joining());
    user.setPassword(passwordEncoder.encode(password));
    user.setFirstAccess(true);

    repository.save(user);
    return AuthenticationRequest.builder()
        .email(user.getEmail())
        .password(password)
        .build();
  }
}