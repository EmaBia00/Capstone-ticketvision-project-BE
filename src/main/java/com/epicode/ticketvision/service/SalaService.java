package com.epicode.ticketvision.service;

import com.epicode.ticketvision.model.Sala;
import com.epicode.ticketvision.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalaService {
    private final SalaRepository salaRepository;

    public List<Sala> getAllSale() {
        return salaRepository.findAll();
    }

    public Sala getSalaById(Long id) {
        return salaRepository.findById(id).orElse(null);
    }

    public Sala createSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public Sala updateSala(Long id, Sala updatedSala) {
        Optional<Sala> salaOptional = salaRepository.findById(id);
        if (salaOptional.isPresent()) {
            Sala sala = salaOptional.get();
            sala.setNumero(updatedSala.getNumero());
            sala.setCapienza(updatedSala.getCapienza());
            sala.setCinema(updatedSala.getCinema());
            return salaRepository.save(sala);
        }
        return null;
    }

    public void deleteSala(Long id) {
        salaRepository.deleteById(id);
    }
}