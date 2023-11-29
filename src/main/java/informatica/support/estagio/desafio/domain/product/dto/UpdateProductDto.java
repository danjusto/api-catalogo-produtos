package informatica.support.estagio.desafio.domain.product.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record UpdateProductDto(
        String title,
        String description,
        @Min(1)
        BigDecimal price,
        @Min(0)
        Integer stock,
        String brand,
        String category,
        String image
) {}
