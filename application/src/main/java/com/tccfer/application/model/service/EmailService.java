package com.tccfer.application.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String activationUrlBase;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${app.activation.url.base}") String activationUrlBase
    ) {
        this.mailSender = mailSender;
        this.activationUrlBase = activationUrlBase;
    }

    /**
     * Envia e-mail de ativação para o cliente.
     * @param recipientEmail e-mail do cliente
     * @param token token gerado para ativação
     */
    public void sendActivationEmail(String recipientEmail, String token) {
        String link = activationUrlBase + "?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Ativação de Conta - Aura Construções");
        message.setText(
                "Olá!\n\n" +
                        "Para ativar sua conta, clique no link abaixo:\n" +
                        link + "\n\n" +
                        "Se você não solicitou este e-mail, ignore-o."
        );
        mailSender.send(message);
    }
}
