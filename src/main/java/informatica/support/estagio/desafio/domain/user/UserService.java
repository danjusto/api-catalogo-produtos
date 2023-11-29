package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.UserRequestDto;
import informatica.support.estagio.desafio.domain.user.dto.UserResponseDto;
import informatica.support.estagio.desafio.domain.user.dto.UserUpdateDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public UserResponseDto executeCreate(UserRequestDto dto) {
        checkIfEmailIsAvailable(dto.email());
        checkIfUsernameIsAvailable(dto.username());
        var user = new User(dto);
        return this.userRepository.save(user).toDto();
    }
    public UserResponseDto executeFindOne(UUID id) {
        return getUser(id).toDto();
    }
    public UserResponseDto executeUpdate(UUID id, UserUpdateDto dto) {
        var user = getUser(id);
        if( dto.email() != null) {
            checkIfEmailIsAvaibleForChange(dto.email(), id);
        }
        user.updateNameAndEmail(dto);
        return this.userRepository.save(user).toDto();
    }
    private User getUser(UUID id) {
        var user = this.userRepository.findById(id);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return user.get();
    }
    private void checkIfEmailIsAvailable(String email) {
        Optional<User> checkUserExists = this.userRepository.findByEmail(email);
        if (checkUserExists.isPresent()) {
            throw new RuntimeException("Email already in use");
        }
    }
    private void checkIfUsernameIsAvailable(String username) {
        Optional<User> checkUserExists = this.userRepository.findByUsername(username);
        if (checkUserExists.isPresent()) {
            throw new RuntimeException("Username already in use");
        }
    }
    private void checkIfEmailIsAvaibleForChange(String email, UUID id) {
        Optional<User> checkUserExists = this.userRepository.findByEmailAndIdNot(email, id);
        if (checkUserExists.isPresent()) {
            throw new RuntimeException("Email already in use");
        }
    }
}
