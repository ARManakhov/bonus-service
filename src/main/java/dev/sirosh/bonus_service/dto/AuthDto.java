package dev.sirosh.bonus_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthDto {
    @NotBlank(message = "username field should not be empty")
    private String username;
    @NotBlank(message = "password field should not be empty")
    private String password;
}
