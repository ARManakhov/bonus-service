package dev.sirosh.bonus_service.service;

import dev.sirosh.bonus_service.entity.Bonus;
import dev.sirosh.bonus_service.entity.User;
import dev.sirosh.bonus_service.repository.BonusRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BonusService {
    private final UserService userService;
    private final BonusRepository repository;

    private Bonus getBonusBySelf() {
        User self = userService.getSelf();
        return self.getBonus();
    }

    @Transactional
    public void add(@Valid int amount) {
        Bonus bonus = getBonusBySelf();
        int newAmount = bonus.getCount() + amount;
        setAmountAndSave(newAmount, bonus);
    }

    @Transactional
    public void subtract(@Valid int amount) {
        Bonus bonus = getBonusBySelf();
        int newAmount = bonus.getCount() - amount;
        setAmountAndSave(newAmount, bonus);
    }

    private void setAmountAndSave(int newAmount, Bonus bonus) {
        if (newAmount < 0) {
            throw new IllegalArgumentException("trying to withdraw more amount than you have");
        }
        bonus.setCount(newAmount);
        repository.save(bonus);
    }
}
