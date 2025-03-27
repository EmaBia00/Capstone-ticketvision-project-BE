package com.epicode.ticketvision.service;

import com.epicode.ticketvision.model.User;
import com.epicode.ticketvision.Enum.Role;
import com.epicode.ticketvision.repository.UserRepository;
import com.epicode.ticketvision.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


     // Registra un nuovo utente e restituisce un token JWT con i dettagli dell'utente.
    public Optional<Map<String, String>> register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return Optional.empty();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());
        return Optional.of(Map.of(
                "id", String.valueOf(user.getId()),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "token", token
        ));
    }

    //Autentica un utente e restituisce un token JWT con i dettagli dell'utente.
    public Optional<Map<String, String>> authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
            User user = userOptional.get();
            String token = jwtUtil.generateToken(user.getEmail());
            return Optional.of(Map.of(
                    "id", String.valueOf(user.getId()),
                    "email", user.getEmail(),
                    "role", user.getRole().name(),
                    "token", token
            ));
        }
        return Optional.empty();
    }
}
