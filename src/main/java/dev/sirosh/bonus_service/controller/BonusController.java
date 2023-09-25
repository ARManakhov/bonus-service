package dev.sirosh.bonus_service.controller;

import dev.sirosh.bonus_service.dto.AmountDto;
import dev.sirosh.bonus_service.service.BonusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bonus")
@RequiredArgsConstructor
public class BonusController {

    private final BonusService service;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bonusAdd(@Valid @RequestBody AmountDto amount) {
        service.add(amount.getAmount());
    }


    @PostMapping("/subtract")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bonusSubtract(@Valid @RequestBody AmountDto amount) {
        service.subtract(amount.getAmount());
    }
}
