package com.daniel.s3.messaging;

import com.daniel.s3.entitites.User;
import com.daniel.s3.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEmailConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = "welcome-emails")
    public void processMessage(User user) {
        try {
            if (user.getEmail() == null || user.getName() == null) {
                log.warn("Mensagem ignorada: email ou nome do usuário está nulo. Usuário: {}", user);
                return;
            }

            emailService.sendWelcomeEmail(user.getEmail(), user.getName());
            log.info("Mensagem processada com sucesso para o usuário: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Erro ao processar mensagem para o usuário {}: {}", user.getEmail(), e.getMessage());
        }
    }
}
