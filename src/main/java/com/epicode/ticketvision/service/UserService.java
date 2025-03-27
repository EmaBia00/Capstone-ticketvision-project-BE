package com.epicode.ticketvision.service;

import com.epicode.ticketvision.Enum.Role;
import com.epicode.ticketvision.Enum.StatoPrenotazione;
import com.epicode.ticketvision.dto.UserUpdateRequest;
import com.epicode.ticketvision.exception.NotFoundException;
import com.epicode.ticketvision.model.Prenotazione;
import com.epicode.ticketvision.model.Spettacolo;
import com.epicode.ticketvision.model.User;
import com.epicode.ticketvision.repository.PrenotazioneRepository;
import com.epicode.ticketvision.repository.SpettacoloRepository;
import com.epicode.ticketvision.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PrenotazioneRepository prenotazioneRepository;
    private final SpettacoloRepository spettacoloRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente non trovato"));
    }

    public User updateUser(Long id, UserUpdateRequest request) {
        User user = getUserById(id);
        if (request.getName() != null) user.setName(request.getName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        return userRepository.save(user);
    }

    public User updateUserRole(Long id, Role newRole) {
        User user = getUserById(id);
        user.setRole(newRole);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        List<Prenotazione> prenotazioni = prenotazioneRepository.findByUtenteId(id);
        for (Prenotazione p : prenotazioni) {
            Spettacolo spettacolo = p.getSpettacolo();
            spettacolo.setPostiDisponibili(spettacolo.getPostiDisponibili() + p.getPostiPrenotati());
            spettacoloRepository.save(spettacolo);
        }
        prenotazioneRepository.deleteAll(prenotazioni);
        userRepository.delete(user);
    }
}