package informatica.support.estagio.desafio.infrastructure.authentication;

import informatica.support.estagio.desafio.infrastructure.exception.AuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthenticationService authService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    public AuthenticationFilter(JwtService jwtService, AuthenticationService authService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        try {
            if (Boolean.TRUE.equals(this.jwtService.validateToken(token))) {
                var subject = this.jwtService.getSubject(token);
                var user = this.authService.loadUserByUsername(subject);
                var authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (AuthException exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
    private String getToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.replace("Bearer", "").trim();
    }
}
