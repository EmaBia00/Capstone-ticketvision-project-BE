package com.epicode.ticketvision.controller;

import com.epicode.ticketvision.model.Prenotazione;
import com.epicode.ticketvision.repository.PrenotazioneRepository;
import com.epicode.ticketvision.service.PrenotazioneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prenotazioni")
@RequiredArgsConstructor
public class PrenotazioneController {
    private final PrenotazioneService prenotazioneService;
    private final PrenotazioneRepository prenotazioneRepository;


    @PostMapping
    public Prenotazione createPrenotazione(@RequestParam Long utenteId,
                                           @RequestParam Long spettacoloId,
                                           @RequestParam int postiPrenotati,
                                           @RequestParam boolean isPaid,
                                           @RequestBody List<Integer> posti) {
        Prenotazione prenotazione = prenotazioneService.createPrenotazione(utenteId, spettacoloId, postiPrenotati, posti, isPaid);

        return prenotazioneRepository.save(prenotazione);
    }

    @GetMapping("/{utenteId}")
    public List<Prenotazione> getPrenotazioniUtente(@PathVariable Long utenteId) {
        return prenotazioneService.getPrenotazioniUtente(utenteId);
    }

    @GetMapping("/spettacolo/{spettacoloId}/posti-occupati")
    public List<Integer> getPostiOccupatiPerSpettacolo(@PathVariable Long spettacoloId) {
        return prenotazioneService.getPostiOccupatiPerSpettacolo(spettacoloId);
    }

    @PutMapping("/{id}/annulla")
    public ResponseEntity<?> annullaPrenotazione(@PathVariable Long id) {
        try {
            prenotazioneService.annullaPrenotazione(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}