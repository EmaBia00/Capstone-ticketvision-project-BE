package com.epicode.ticketvision.service;

import com.epicode.ticketvision.model.Film;
import com.epicode.ticketvision.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepository;

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    public Film updateFilm(Long id, Film updatedFilm) {
        Optional<Film> filmOptional = filmRepository.findById(id);
        if (filmOptional.isPresent()) {
            Film film = filmOptional.get();
            film.setTitle(updatedFilm.getTitle());
            film.setSynopsis(updatedFilm.getSynopsis());
            film.setPosterUrl(updatedFilm.getPosterUrl());
            film.setBannerUrl(updatedFilm.getBannerUrl());
            film.setDuration(updatedFilm.getDuration());
            film.setActors(updatedFilm.getActors());
            film.setOscarNominations(updatedFilm.getOscarNominations());
            film.setPrice(updatedFilm.getPrice());
            film.setFeatured(updatedFilm.isFeatured());
            return filmRepository.save(film);
        }
        return null;
    }

    public void deleteFilm(Long id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Film non trovato con ID " + id));
        filmRepository.delete(film);
    }
}
