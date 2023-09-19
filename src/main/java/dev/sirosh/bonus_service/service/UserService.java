package dev.sirosh.bonus_service.service;


import dev.sirosh.bonus_service.dto.AuthDto;
import dev.sirosh.bonus_service.entity.Bonus;
import dev.sirosh.bonus_service.entity.Role;
import dev.sirosh.bonus_service.entity.User;
import dev.sirosh.bonus_service.repository.UserRepository;
import dev.sirosh.bonus_service.security.JwtManager;
import dev.sirosh.bonus_service.security.JwtTokenAuthentication;
import dev.sirosh.bonus_service.security.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

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

    public User getSelf() {
        JwtTokenAuthentication authentication = (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getDetails();
        User user = userDetails.getUser();
        if (isNull(user.getBonus())) {
            user.setBonus(new Bonus());
        }
        return user;
    }

    public List<User> getList(){
        return repository.findAll();
    }
}
