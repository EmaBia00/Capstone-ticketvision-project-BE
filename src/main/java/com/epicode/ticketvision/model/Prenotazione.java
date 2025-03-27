package com.epicode.ticketvision.model;

import com.epicode.ticketvision.Enum.StatoPrenotazione;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User utente;

    @ManyToOne
    private Spettacolo spettacolo;

    private int postiPrenotati;

    @ElementCollection
    @CollectionTable(name = "prenotazione_posti", joinColumns = @JoinColumn(name = "prenotazione_id"))
    @Column(name = "posto_numero")
    private List<Integer> posti;

    @Enumerated(EnumType.STRING)
    private StatoPrenotazione stato;
}