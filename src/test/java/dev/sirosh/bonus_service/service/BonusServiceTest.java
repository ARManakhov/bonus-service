package dev.sirosh.bonus_service.service;

import dev.sirosh.bonus_service.entity.Bonus;
import dev.sirosh.bonus_service.entity.User;
import dev.sirosh.bonus_service.repository.BonusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.sirosh.bonus_service.Utils.buildUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BonusServiceTest {
    @Mock
    UserService userService;
    @Mock
    BonusRepository repository;
    @InjectMocks
    BonusService bonusService;

    @Captor
    ArgumentCaptor<Bonus> actual;

    @Test
    void add_normal() {
        User user = buildUser(0);
        when(userService.getSelf()).thenReturn(user);

        bonusService.add(10);

        verify(repository).save(actual.capture());

        Bonus expected = buildUser(10).getBonus();
        assertThat(actual.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void add_tryAddNegative() {
        User user = buildUser(0);
        when(userService.getSelf()).thenReturn(user);

        assertThrows(IllegalArgumentException.class,
                () -> bonusService.add(-10),
                "trying to withdraw more amount than you have");
    }


    @Test
    void subtract_normal() {
        User user = buildUser(10);
        when(userService.getSelf()).thenReturn(user);

        bonusService.subtract(10);

        verify(repository).save(actual.capture());

        Bonus expected = buildUser(0).getBonus();
        assertThat(actual.getValue())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }


    @Test
    void subtract_tryWithdrawMore() {
        User user = buildUser(10);
        when(userService.getSelf()).thenReturn(user);

        assertThrows(IllegalArgumentException.class,
                () -> bonusService.subtract(100),
                "trying to withdraw more amount than you have");
    }
}