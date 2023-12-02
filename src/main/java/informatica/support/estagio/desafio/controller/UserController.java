package informatica.support.estagio.desafio.controller;

import informatica.support.estagio.desafio.domain.user.LoginService;
import informatica.support.estagio.desafio.domain.user.UserService;
import informatica.support.estagio.desafio.domain.user.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto findOne(Principal principal) {
        return this.userService.executeFindOne(principal);
    }
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto update(Principal principal, @RequestBody @Valid UserUpdateDto dto) {
        return this.userService.executeUpdate(principal, dto);
    }
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(Principal principal) {
        this.userService.executeRemove(principal);
    }
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto login(@RequestBody @Valid LoginDto dto) {
        return this.loginService.execute(dto);
    }
}
