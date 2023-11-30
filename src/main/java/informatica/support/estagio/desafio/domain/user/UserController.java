package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final LoginService loginService;
    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
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
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id) {
        this.userService.executeRemove(id);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto login(@RequestBody @Valid LoginDto dto) {
        return this.loginService.execute(dto);
    }
}
