package com.daniel.s3.resources;

import com.daniel.s3.entitites.User;
import com.daniel.s3.services.S3Service;
import com.daniel.s3.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;
    private final S3Service s3Service;


    @PostMapping("/upload")
    public ResponseEntity<User> uploadUser(@ModelAttribute("user") User user, @RequestPart("image") MultipartFile image) throws Exception {
        String imageKey = s3Service.uploadImage(image);
        User savedUser = userService.saveUser(user, imageKey);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(uri).body(savedUser);
    }
}
