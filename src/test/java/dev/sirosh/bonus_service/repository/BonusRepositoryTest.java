package dev.sirosh.bonus_service.repository;

import dev.sirosh.bonus_service.AbstractIntegrationTest;
import dev.sirosh.bonus_service.entity.Bonus;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BonusRepositoryTest extends AbstractIntegrationTest {
    @Autowired
    BonusRepository bonusRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Sql(scripts = "classpath:/test_user_bonus_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void save_normal() {
        Bonus bonus = bonusRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("test failed, incorrect seed (no bonus with id 0)"));
        bonus.setCount(10);

        bonusRepository.save(bonus);

        Optional<Bonus> actual = bonusRepository.findById(0L);
        Bonus expected = new Bonus();
        expected.setCount(10);
        expected.setId(0L);
        assertThat(actual).isPresent();
        assertThat(actual.get())
                .usingRecursiveComparison()
                .ignoringFields("user")
                .isEqualTo(expected);
    }

    @Test
    @Sql(scripts = "classpath:/test_user_bonus_seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:/test_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void save_negative() {
        Bonus bonus = bonusRepository.findById(0L)
                .orElseThrow(() -> new EntityNotFoundException("test failed, incorrect seed (no bonus with id 0)"));
        bonus.setCount(-10);

        assertThrows(DataIntegrityViolationException.class, () -> bonusRepository.save(bonus));
    }
}