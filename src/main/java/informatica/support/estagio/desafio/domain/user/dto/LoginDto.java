package informatica.support.estagio.desafio.domain.user.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {}
