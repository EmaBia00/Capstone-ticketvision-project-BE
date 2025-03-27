package com.epicode.ticketvision.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Spettacolo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Film film;

    @ManyToOne
    private Sala sala;

    private LocalDateTime orario;
    private int postiDisponibili;
}