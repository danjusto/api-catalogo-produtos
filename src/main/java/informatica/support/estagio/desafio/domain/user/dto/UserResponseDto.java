package informatica.support.estagio.desafio.domain.user.dto;

import java.util.UUID;

public record UserResponseDto(UUID id, String name, String username, String email) {
}
