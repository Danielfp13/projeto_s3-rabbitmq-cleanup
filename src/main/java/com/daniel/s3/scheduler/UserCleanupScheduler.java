package com.daniel.s3.scheduler;

import com.daniel.s3.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCleanupScheduler {

    private final UserService userService;

    @Scheduled(fixedRate = 3600000) // Intervalo de 1 hora (em milissegundos)
    public void cleanUpOldUsers() {
        log.info("Iniciando limpeza de usuários antigos...");
        var usersToDelete = userService.findUsersOlderThan24Hours();

        usersToDelete.forEach(user -> {
            try {
                userService.deleteUser(user.getId());
                log.info("Usuário com ID {} removido com sucesso.", user.getId());
            } catch (Exception e) {
                log.error("Erro ao remover usuário com ID {}: {}", user.getId(), e.getMessage());
            }
        });

        log.info("Limpeza de usuários concluída.");
    }
}
