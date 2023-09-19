package dev.sirosh.bonus_service.dto;

import dev.sirosh.bonus_service.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private BonusDto bonus;
}
