package com.epicode.ticketvision.controller;

import com.epicode.ticketvision.model.User;
import com.epicode.ticketvision.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // Permetti richieste dal frontend
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<Map<String, String>> response = authService.register(user);
        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body(Map.of("error", "L'email è già registrata!")));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        Optional<Map<String, String>> response = authService.authenticate(credentials.get("email"), credentials.get("password"));
        return response.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Credenziali non valide")));
    }
}
