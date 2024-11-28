package org.pragadeesh.taskmanagement.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "Welcome to Authenticated URL";
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(Map.of(
            "username", userDetails.getUsername(),
            "authorities", userDetails.getAuthorities()
        ));
    }
}
