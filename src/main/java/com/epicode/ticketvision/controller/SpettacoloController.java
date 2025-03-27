package com.epicode.ticketvision.controller;

import com.epicode.ticketvision.model.Spettacolo;
import com.epicode.ticketvision.service.PrenotazioneService;
import com.epicode.ticketvision.service.SpettacoloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spettacoli")
@RequiredArgsConstructor
public class SpettacoloController {
    private final SpettacoloService spettacoloService;
    private final PrenotazioneService prenotazioneService;

    @GetMapping
    public List<Spettacolo> getAllSpettacoli() {
        return spettacoloService.getAllSpettacoli();
    }
    @GetMapping("/{id}")
    public Spettacolo getSpettacoloById(@PathVariable Long id) {
        return spettacoloService.getSpettacoloById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Spettacolo createSpettacolo(@RequestBody Spettacolo spettacolo) {
        return spettacoloService.createSpettacolo(spettacolo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Spettacolo updateSpettacolo(@PathVariable Long id, @RequestBody Spettacolo spettacolo) {
        return spettacoloService.updateSpettacolo(id, spettacolo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteSpettacolo(@PathVariable Long id) {
        try {
            spettacoloService.deleteSpettacolo(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/posti-occupati")
    public List<Integer> getPostiOccupatiPerSpettacolo(@PathVariable Long id) {
        return prenotazioneService.getPostiOccupatiPerSpettacolo(id);
    }
}
