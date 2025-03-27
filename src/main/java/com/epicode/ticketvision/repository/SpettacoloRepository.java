package com.epicode.ticketvision.repository;

import com.epicode.ticketvision.model.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpettacoloRepository extends JpaRepository<Spettacolo, Long> {
}