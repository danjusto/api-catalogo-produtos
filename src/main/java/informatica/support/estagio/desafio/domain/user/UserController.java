package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.UserRequestDto;
import informatica.support.estagio.desafio.domain.user.dto.UserResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto create(@RequestBody @Valid UserRequestDto dto) {
        return this.userService.executeCreate(dto);
    }
}