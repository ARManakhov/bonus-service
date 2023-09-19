package dev.sirosh.bonus_service.dto;

import dev.sirosh.bonus_service.entity.Role;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private Role role;
}
