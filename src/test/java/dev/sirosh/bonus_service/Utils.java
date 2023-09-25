package dev.sirosh.bonus_service;

import dev.sirosh.bonus_service.entity.Bonus;
import dev.sirosh.bonus_service.entity.Role;
import dev.sirosh.bonus_service.entity.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
    public static User buildUser(int count) {
        Bonus bonus = new Bonus();
        bonus.setCount(count);
        User user = User.builder().role(Role.USER)
                .username("test_user")
                .bonus(bonus)
                .build();
        bonus.setUser(user);
        return user;
    }
}
