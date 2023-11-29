package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.UserRequestDto;
import informatica.support.estagio.desafio.domain.user.dto.UserResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public UserResponseDto executeCreate(UserRequestDto dto) {
        checkIfEmailIsAvaible(dto.email());
        checkIfUsernameIsAvaible(dto.username());
        var user = new User(dto);
        return this.userRepository.save(user).toDto();
    }

    private void checkIfEmailIsAvaible(String email) {
        Optional<User> checkUserExists = this.userRepository.findByEmail(email);
        if (checkUserExists.isPresent()) {
            throw new RuntimeException("Email already in use");
        }
    }
    private void checkIfUsernameIsAvaible(String username) {
        Optional<User> checkUserExists = this.userRepository.findByUsername(username);
        if (checkUserExists.isPresent()) {
            throw new RuntimeException("Username already in use");
        }
    }
}
