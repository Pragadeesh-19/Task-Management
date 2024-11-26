package org.pragadeesh.taskmanagement.service;

import lombok.RequiredArgsConstructor;

import org.pragadeesh.taskmanagement.Exception.UserAlreadyExistsException;
import org.pragadeesh.taskmanagement.dto.UserLoginDto;
import org.pragadeesh.taskmanagement.dto.UserSignupDto;
import org.pragadeesh.taskmanagement.model.User;
import org.pragadeesh.taskmanagement.repository.UserRepository;
import org.pragadeesh.taskmanagement.util.JwtResponse;
import org.pragadeesh.taskmanagement.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public User register(UserSignupDto request) {

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with username: " + request.getUsername());
        }

        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to register user" + e.getMessage());
        }
    }

    public JwtResponse login(UserLoginDto request) {

        Objects.requireNonNull(request, "Login request cannot be null");
        Objects.requireNonNull(request.getUsername(), "username cannot be null");
        Objects.requireNonNull(request.getPassword(), "Password cannot be null");

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("user not found with username: " + request.getUsername()));

            String token = jwtUtil.generateToken(user);

            if (token == null || token.trim().isEmpty()) {
                throw new IllegalStateException("Generated token cannot be empty");
            }

            return new JwtResponse(token, user);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed" + e.getMessage());
        }
    }
}
