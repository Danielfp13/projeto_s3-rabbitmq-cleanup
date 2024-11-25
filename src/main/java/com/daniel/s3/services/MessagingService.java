package com.daniel.s3.services;

import com.daniel.s3.entitites.User;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    public void sendMessage(User user) {
        try {
            rabbitTemplate.convertAndSend(queueName, user);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para a fila: " + e.getMessage(), e);
        }
    }
}
