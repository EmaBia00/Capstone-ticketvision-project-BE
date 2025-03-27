package com.epicode.ticketvision.controller;

import com.epicode.ticketvision.model.Sala;
import com.epicode.ticketvision.service.SalaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SalaController {
    private final SalaService salaService;

    @GetMapping
    public List<Sala> getSale() {
        return salaService.getAllSale();
    }
    @GetMapping("/{id}")
    public Sala getSalaById(@PathVariable Long id) {
        return salaService.getSalaById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Sala createSala(@RequestBody Sala sala) {
        return salaService.createSala(sala);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Sala updateSala(@PathVariable Long id, @RequestBody Sala sala) {
        return salaService.updateSala(id, sala);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSala(@PathVariable Long id) {
        try {
            salaService.deleteSala(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}