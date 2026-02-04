package pt.estg.projetoes202526.repositories;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import pt.estg.projetoes202526.domain.dto.UserDTO;
import pt.estg.projetoes202526.domain.models.Role;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.domain.dto.RegisterDTO;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // Força o H2
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get user successfully from DB")
    void findByEmailCase1() {
        String mail = "chuck@norris.com";
        // Certificar que a Role funciona
        Role userRole = new Role("ADMIN");
        this.entityManager.persist(userRole); // Persistir na role primeiro, caso necessário

        UserDTO data = new UserDTO("chuck", mail, "123", userRole);
        this.createUser(data);

        User result = (User) this.userRepository.findByEmail(mail);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName("Should not get user from DB when user not exists")
    void findByEmailCase2() {
        String mail = "chuck@norris.com";
        // Certificar que a Role funciona
        Role userRole = new Role("ADMIN");
        this.entityManager.persist(userRole); // Persistir na role primeiro, caso necessário

        User result = (User) this.userRepository.findByEmail(mail);
        assertThat(result).isNull();
    }

    private User createUser(UserDTO data) {
        User newUser = new User(data);
        this.entityManager.persist(newUser);
        return newUser;
    }
}