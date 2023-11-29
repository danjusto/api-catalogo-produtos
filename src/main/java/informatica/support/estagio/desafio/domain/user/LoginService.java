package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.LoginDto;
import informatica.support.estagio.desafio.domain.user.dto.TokenDto;
import informatica.support.estagio.desafio.infrastructure.authentication.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public LoginService(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public TokenDto execute(LoginDto dto) throws AuthenticationException {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        authManager.authenticate(authToken);
        var token = jwtService.generateToken(dto.username());
        return new TokenDto(token);
    }
}
