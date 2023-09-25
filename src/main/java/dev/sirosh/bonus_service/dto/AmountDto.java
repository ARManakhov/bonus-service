package dev.sirosh.bonus_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmountDto {
    @NotNull(message = "amount value should be present")
    @Min(value = 0, message = "amount should be positive value")
    private Integer amount;
}
