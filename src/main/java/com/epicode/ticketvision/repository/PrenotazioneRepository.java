package com.epicode.ticketvision.repository;

import com.epicode.ticketvision.Enum.StatoPrenotazione;
import com.epicode.ticketvision.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    List<Prenotazione> findByUtenteId(Long utenteId);
    List<Prenotazione> findBySpettacoloIdAndStato(Long spettacoloId, StatoPrenotazione stato);
    @Query("SELECT p FROM Prenotazione p WHERE p.spettacolo.id = :spettacoloId AND p.stato IN :stati")
    List<Prenotazione> findBySpettacoloIdAndStatoIn(
            @Param("spettacoloId") Long spettacoloId,
            @Param("stati") List<StatoPrenotazione> stati
    );
}
