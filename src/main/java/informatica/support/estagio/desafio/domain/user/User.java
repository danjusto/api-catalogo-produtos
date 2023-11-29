package informatica.support.estagio.desafio.domain.user;

import informatica.support.estagio.desafio.domain.user.dto.UserRequestDto;
import informatica.support.estagio.desafio.domain.user.dto.UserResponseDto;
import informatica.support.estagio.desafio.domain.user.dto.UserUpdateDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String password;

    public User(UserRequestDto dto) {
        this.name = dto.name();
        this.username = dto.username();
        this.email = dto.email();
        this.password = dto.password();
    }
    public UserResponseDto toDto() {
        return new UserResponseDto(this.id, this.name, this.username, this.email);
    }
    public void updateNameAndEmail(UserUpdateDto dto) {
        if (dto.name() != null) {
            this.name = dto.name();
        }
        if (dto.email() != null) {
            this.email = dto.email();
        }
    }
}
