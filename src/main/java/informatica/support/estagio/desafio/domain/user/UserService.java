package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.*;
import informatica.support.estagio.desafio.infrastructure.exception.AlreadyExistException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public UserResponseDto executeCreate(UserRequestDto dto) {
        checkIfEmailIsAvailable(dto.email());
        checkIfUsernameIsAvailable(dto.username());
        var user = new User(dto);
        user.setPassword(this.passwordEncoder.encode(dto.password()));
        return this.userRepository.save(user).toDto();
    }
    public UserResponseDto executeFindOne(Principal principal) {
        return getUserByUsername(principal.getName()).toDto();
    }
    @Transactional
    public UserResponseDto executeUpdate(Principal principal, UserUpdateDto dto) {
        var user = getUserByUsername(principal.getName());
        if( dto.email() != null) {
            checkIfEmailIsAvailableForChange(dto.email(), user.getId());
        }
        user.updateNameAndEmail(dto);
        return this.userRepository.save(user).toDto();
    }
    @Transactional
    public void executeRemove(Principal principal) {
        var user = getUserByUsername(principal.getName());
        this.userRepository.delete(user);
    }
    private User getUserByUsername(String username) {
        var user = this.userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        return user.get();
    }
    private void checkIfEmailIsAvailable(String email) {
        Optional<User> checkUserExists = this.userRepository.findByEmail(email);
        if (checkUserExists.isPresent()) {
            throw new AlreadyExistException("Email already in use");
        }
    }
    private void checkIfUsernameIsAvailable(String username) {
        Optional<User> checkUserExists = this.userRepository.findByUsername(username);
        if (checkUserExists.isPresent()) {
            throw new AlreadyExistException("Username already in use");
        }
    }
    private void checkIfEmailIsAvailableForChange(String email, UUID id) {
        Optional<User> checkUserExists = this.userRepository.findByEmailAndIdNot(email, id);
        if (checkUserExists.isPresent()) {
            throw new AlreadyExistException("Email already in use");
        }
    }
}
