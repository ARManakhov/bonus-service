package dev.sirosh.bonus_service.service;


import dev.sirosh.bonus_service.dto.AuthDto;
import dev.sirosh.bonus_service.entity.Role;
import dev.sirosh.bonus_service.entity.User;
import dev.sirosh.bonus_service.repository.UserRepository;
import dev.sirosh.bonus_service.security.JwtManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final JwtManager jwtManager;
    private final PasswordEncoder passwordEncoder;

    public void register(AuthDto auth) {
        Optional<User> byUsername = repository.findByUsername(auth.getUsername());
        if (byUsername.isPresent()) {
            throw new IllegalArgumentException("user already exists");
        }
        User user = User.builder()
                .username(auth.getUsername())
                .password(passwordEncoder.encode(auth.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
    }

    public String login(AuthDto authDto) {
        User user = repository.findByUsername(authDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("user not found"));
        if (!passwordEncoder.matches(authDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("wrong password !");
        }
        return jwtManager.create(user);
    }
}
