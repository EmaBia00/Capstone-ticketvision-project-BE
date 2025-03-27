package com.epicode.ticketvision.repository;

import com.epicode.ticketvision.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
