package com.daniel.s3.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Envia um e-mail de boas-vindas.
     * 
     * @param toEmail Email do destinatário
     * @param userName Nome do usuário
     */
    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(toEmail);
            helper.setSubject("Bem-vindo à Plataforma!");
            helper.setText("<h1>Olá, " + userName + "</h1><p>Estamos felizes em ter você conosco!</p>", true);
            helper.setFrom(fromEmail);

            mailSender.send(mimeMessage);
            log.info("E-mail de boas-vindas enviado para {}", toEmail);
        } catch (MessagingException e) {
            log.error("Erro ao enviar e-mail para {}: {}", toEmail, e.getMessage());
            throw new RuntimeException("Falha ao enviar e-mail", e); // Opcional: propagar exceção
        }
    }
}
