package dev.sirosh.bonus_service.controller;

import dev.sirosh.bonus_service.dto.AuthDto;
import dev.sirosh.bonus_service.dto.UserDto;
import dev.sirosh.bonus_service.mapper.UserMapper;
import dev.sirosh.bonus_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper userMapper;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/register")
    public void register(@Valid @RequestBody AuthDto user) {
        service.register(user);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody AuthDto user) {
        return service.login(user);
    }

    @GetMapping("/me")
    public UserDto getSelf() {
        return userMapper.toUserDto(service.getSelf());
    }

    @GetMapping
    public List<UserDto> getList() {
        return userMapper.toUserDto(service.getList());
    }
}
