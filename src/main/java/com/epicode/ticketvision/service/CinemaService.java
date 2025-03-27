package com.epicode.ticketvision.service;

import com.epicode.ticketvision.model.Cinema;
import com.epicode.ticketvision.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;

    public List<Cinema> getAllCinemas() {
        return cinemaRepository.findAll();
    }

    public Cinema getCinemaById(Long id) {
        return cinemaRepository.findById(id).orElse(null);
    }

    public Cinema createCinema(Cinema cinema) {
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(Long id, Cinema updatedCinema) {
        Optional<Cinema> cinemaOptional = cinemaRepository.findById(id);
        if (cinemaOptional.isPresent()) {
            Cinema cinema = cinemaOptional.get();
            cinema.setName(updatedCinema.getName());
            cinema.setAddress(updatedCinema.getAddress());
            return cinemaRepository.save(cinema);
        }
        return null;
    }

    public void deleteCinema(Long id) {
        cinemaRepository.deleteById(id);
    }
}

