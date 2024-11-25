package com.daniel.s3.services;

import com.daniel.s3.entitites.User;
import com.daniel.s3.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final S3Service s3Service;
    private final MessagingService messagingService;

    public User saveUser(User user, String imageKey) {
        user.setImageLink(imageKey);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        messagingService.sendMessage(savedUser);
        return savedUser;
    }


    public void deleteUser(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            s3Service.deleteImage(user.getImageLink());
            userRepository.delete(user);
        }
    }

    public List<User> findUsersOlderThan24Hours() {
        return userRepository.findUsersOlderThan(LocalDateTime.now().minusHours(24));
    }
}
