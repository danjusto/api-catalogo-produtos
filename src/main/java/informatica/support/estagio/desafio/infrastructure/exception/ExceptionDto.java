package informatica.support.estagio.desafio.infrastructure.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionDto(
        HttpStatus statusCode,
        String message,
        String dateTime

) {
}
