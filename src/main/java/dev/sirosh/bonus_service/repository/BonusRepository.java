package dev.sirosh.bonus_service.repository;

import dev.sirosh.bonus_service.entity.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, Long> {
}
