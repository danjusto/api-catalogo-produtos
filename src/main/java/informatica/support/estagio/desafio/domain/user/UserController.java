package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.UserRequestDto;
import informatica.support.estagio.desafio.domain.user.dto.UserResponseDto;
import informatica.support.estagio.desafio.domain.user.dto.UserUpdateDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto findOne(@PathVariable UUID id) {
        return this.userService.executeFindOne(id);
    }
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto update(@PathVariable UUID id, @RequestBody @Valid UserUpdateDto dto) {
        return this.userService.executeUpdate(id, dto);
    }
}
