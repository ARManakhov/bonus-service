package dev.sirosh.bonus_service.controller;

import dev.sirosh.bonus_service.dto.AuthDto;
import dev.sirosh.bonus_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthDto user) {
        service.register(user);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthDto user) {
        return service.login(user);
    }
}
