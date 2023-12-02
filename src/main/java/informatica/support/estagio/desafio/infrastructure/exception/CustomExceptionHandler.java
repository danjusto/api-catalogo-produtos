package informatica.support.estagio.desafio.infrastructure.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j(topic = "CUSTOM-EXCEPTION-HANDLER")
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(HttpStatus.BAD_REQUEST, "Invalid parameter", LocalDateTime.now().format(formatter)));
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ArgNotValidDTO> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(ArgNotValidDTO::new).toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now().format(formatter)));
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleAlreadyInUseException(AlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionDto(HttpStatus.BAD_REQUEST, ex.getMessage(), LocalDateTime.now().format(formatter)));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now().format(formatter)));
    }
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now().format(formatter)));
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDto(HttpStatus.NOT_FOUND, ex.getMessage(), LocalDateTime.now().format(formatter)));
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleExpiredJwt(AuthException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionDto(HttpStatus.UNAUTHORIZED, ex.getMessage(), LocalDateTime.now().format(formatter)));
    }
    private record ArgNotValidDTO(String field, String message) {
        public ArgNotValidDTO(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
