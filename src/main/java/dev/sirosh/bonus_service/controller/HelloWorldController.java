package dev.sirosh.bonus_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("hello_world")
    private String helloWorld() {
        return "helloWorld";
    }
}
