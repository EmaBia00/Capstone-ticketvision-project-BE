package com.epicode.ticketvision.service;

import com.epicode.ticketvision.Enum.StatoPrenotazione;
import com.epicode.ticketvision.model.Prenotazione;
import com.epicode.ticketvision.model.Spettacolo;
import com.epicode.ticketvision.model.User;
import com.epicode.ticketvision.repository.PrenotazioneRepository;
import com.epicode.ticketvision.repository.SpettacoloRepository;
import com.epicode.ticketvision.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioneRepository;
    private final SpettacoloRepository spettacoloRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<Prenotazione> getPrenotazioniUtente(Long utenteId) {
        return prenotazioneRepository.findByUtenteId(utenteId);
    }

    public List<Integer> getPostiOccupatiPerSpettacolo(Long spettacoloId) {
        List<Prenotazione> prenotazioni = prenotazioneRepository.findBySpettacoloIdAndStatoIn(
                spettacoloId,
                List.of(StatoPrenotazione.CONFERMATA, StatoPrenotazione.PAGATO)
        );
        return prenotazioni.stream()
                .flatMap(p -> p.getPosti().stream())
                .collect(Collectors.toList());
    }

    public Prenotazione createPrenotazione(Long utenteId, Long spettacoloId, int postiPrenotati, List<Integer> posti, boolean isPaid) {
        Optional<User> utenteOptional = userRepository.findById(utenteId);
        Optional<Spettacolo> spettacoloOptional = spettacoloRepository.findById(spettacoloId);

        if (utenteOptional.isPresent() && spettacoloOptional.isPresent()) {
            Spettacolo spettacolo = spettacoloOptional.get();

            // Verifica che i posti siano disponibili
            List<Integer> postiOccupati = getPostiOccupatiPerSpettacolo(spettacoloId);
            if (posti.stream().anyMatch(postiOccupati::contains)) {
                throw new IllegalStateException("Alcuni posti sono già occupati");
            }

            spettacolo.setPostiDisponibili(spettacolo.getPostiDisponibili() - postiPrenotati);
            spettacoloRepository.save(spettacolo);

                Prenotazione prenotazione = new Prenotazione();
                prenotazione.setUtente(utenteOptional.get());
                prenotazione.setSpettacolo(spettacolo);
                prenotazione.setPostiPrenotati(postiPrenotati);
                prenotazione.setPosti(posti);
                prenotazione.setStato(isPaid ? StatoPrenotazione.PAGATO : StatoPrenotazione.CONFERMATA);

                Prenotazione savedPrenotazione = prenotazioneRepository.save(prenotazione);
                emailService.sendPrenotazioneConferma(savedPrenotazione);

                return savedPrenotazione;
        }
        throw new IllegalStateException("Utente o spettacolo non trovati, o posti insufficienti");
    }

    public void annullaPrenotazione(Long prenotazioneId) {
        Optional<Prenotazione> prenotazioneOptional = prenotazioneRepository.findById(prenotazioneId);
        if (prenotazioneOptional.isPresent()) {
            Prenotazione prenotazione = prenotazioneOptional.get();
            prenotazione.setStato(StatoPrenotazione.ANNULLATA);

            Spettacolo spettacolo = prenotazione.getSpettacolo();
            spettacolo.setPostiDisponibili(spettacolo.getPostiDisponibili() + prenotazione.getPostiPrenotati());
            spettacoloRepository.save(spettacolo);

            prenotazioneRepository.save(prenotazione);
        }
    }
}
