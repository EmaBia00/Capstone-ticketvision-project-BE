package com.epicode.ticketvision.repository;

import com.epicode.ticketvision.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Long> {
}