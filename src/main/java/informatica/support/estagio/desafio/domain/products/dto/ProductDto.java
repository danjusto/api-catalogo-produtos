package informatica.support.estagio.desafio.domain.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDto(
        UUID id,
        @NotBlank
        String title,
        String description,
        @NotNull
        @Min(1)
        BigDecimal price,
        @NotNull
        @Min(0)
        Integer stock,
        @NotBlank
        String brand,
        @NotBlank
        String category,
        String image
) {
}
