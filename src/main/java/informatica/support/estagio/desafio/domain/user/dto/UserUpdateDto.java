package informatica.support.estagio.desafio.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDto(
        String name,
        @Email(message = "Invalid email")
        String email
) {
}
