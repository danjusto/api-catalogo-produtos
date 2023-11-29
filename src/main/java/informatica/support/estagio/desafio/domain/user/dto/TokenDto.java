package informatica.support.estagio.desafio.domain.user.dto;

public record TokenDto(String type, String token) {
    public TokenDto(String token) {
        this("Bearer", token);
    }
}
