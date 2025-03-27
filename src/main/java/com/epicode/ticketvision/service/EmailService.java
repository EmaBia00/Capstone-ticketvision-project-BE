package com.epicode.ticketvision.service;

import com.epicode.ticketvision.Enum.StatoPrenotazione;
import com.epicode.ticketvision.model.Prenotazione;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    String fromEmail;

    public void sendPrenotazioneConferma(Prenotazione prenotazione) {
        String toEmail = fromEmail;
        String subject = "Conferma Prenotazione - TicketVision";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy - HH:mm", Locale.ITALIAN);
        String dataFormattata = prenotazione.getSpettacolo().getOrario().format(formatter);

        String postiPrenotatiStr = prenotazione.getPosti().stream()
                .sorted()
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        String paymentStatus = prenotazione.getStato() == StatoPrenotazione.PAGATO
                ? "<span style='color: green; font-weight: bold;'>PAGATO</span>"
                : "<span style='color: orange; font-weight: bold;'>DA PAGARE IN CASSA</span>";

        String body = "<h2>Prenotazione Confermata 🎟️</h2>" +
                "<p>Ciao <b>" + prenotazione.getUtente().getName() + "</b>,</p>" +
                "<p>La tua prenotazione per lo spettacolo <b>" + prenotazione.getSpettacolo().getFilm().getTitle() + "</b> è stata confermata!</p>" +
                "<p><b>Data:</b> " + dataFormattata + "<br>" +
                "<b>Posti prenotati:</b> " + prenotazione.getPostiPrenotati() + " (Posti: " + postiPrenotatiStr + ")<br>" +
                "<b>Cinema:</b> " + prenotazione.getSpettacolo().getSala().getCinema().getName() + "<br>" +
                "<b>Sala:</b> " + prenotazione.getSpettacolo().getSala().getNumero() + "<br>" +
                "<b>Stato pagamento:</b> " + paymentStatus + "</p>" +
                "<p>Grazie per aver scelto TicketVision! 🎬</p>";

        sendEmail(toEmail, subject, body);
    }

    private void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
