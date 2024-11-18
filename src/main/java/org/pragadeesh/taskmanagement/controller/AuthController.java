package org.pragadeesh.taskmanagement.controller;

import io.jsonwebtoken.Jwt;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.pragadeesh.taskmanagement.dto.UserLoginDto;
import org.pragadeesh.taskmanagement.dto.UserSignupDto;
import org.pragadeesh.taskmanagement.service.AuthenticationService;
import org.pragadeesh.taskmanagement.util.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth/")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management API's")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register new user",
            description = "Register a new user with provided credentials"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",
                content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "User already exist")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserSignupDto userSignupDto) {
        return ResponseEntity.ok(authenticationService.register(userSignupDto));
    }

    @Operation(
            summary = "Authenticate User",
            description = "Login with username and password to receive JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        // return ResponseEntity.ok(authenticationService.login(userLoginDto));

        if (userLoginDto ==  null) {
            return ResponseEntity.badRequest().body("login request cannot be null");
        }

        if (userLoginDto.getUsername() == null || userLoginDto.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }

        if (userLoginDto.getPassword() == null || userLoginDto.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be empty");
        }

        try {
            JwtResponse response = authenticationService.login(userLoginDto);
            if (response == null || response.getToken() == null) {
                return ResponseEntity.internalServerError().body("Error getting authentication token");
            }
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occured during authentication");
        }
    }
}
