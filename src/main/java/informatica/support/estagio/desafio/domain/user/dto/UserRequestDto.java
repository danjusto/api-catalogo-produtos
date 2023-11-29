package informatica.support.estagio.desafio.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequestDto(
        @NotBlank(message = "'name' must not be blank")
        String name,
        @NotBlank(message = "'username' must not be blank")
        @Size(min = 6, message = "'username' must have at least 6 characters")
        String username,
        @Email(message = "Invalid email")
        @NotNull(message = "'email' must not be null")
        String email,
        @NotBlank(message = "'password' must not be blank")
        @Size(min = 8, message = "'password' must have at least 8 characters")
        String password) {
}
