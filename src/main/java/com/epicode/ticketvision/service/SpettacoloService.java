package com.epicode.ticketvision.service;

import com.epicode.ticketvision.model.Spettacolo;
import com.epicode.ticketvision.repository.SpettacoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpettacoloService {
    private final SpettacoloRepository spettacoloRepository;

    public List<Spettacolo> getAllSpettacoli() {
        return spettacoloRepository.findAll();
    }

    public Spettacolo getSpettacoloById(Long id) {
        return spettacoloRepository.findById(id).orElse(null);
    }

    public Spettacolo createSpettacolo(Spettacolo spettacolo) {
        return spettacoloRepository.save(spettacolo);
    }

    public Spettacolo updateSpettacolo(Long id, Spettacolo updatedSpettacolo) {
        Optional<Spettacolo> spettacoloOptional = spettacoloRepository.findById(id);
        if (spettacoloOptional.isPresent()) {
            Spettacolo spettacolo = spettacoloOptional.get();
            spettacolo.setFilm(updatedSpettacolo.getFilm());
            spettacolo.setSala(updatedSpettacolo.getSala());
            spettacolo.setOrario(updatedSpettacolo.getOrario());
            spettacolo.setPostiDisponibili(updatedSpettacolo.getPostiDisponibili());
            return spettacoloRepository.save(spettacolo);
        }
        return null;
    }

    public void deleteSpettacolo(Long id) {
        spettacoloRepository.deleteById(id);
    }
}
