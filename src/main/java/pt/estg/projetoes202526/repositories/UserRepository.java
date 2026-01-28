package pt.estg.projetoes202526.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import pt.estg.projetoes202526.domain.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    UserDetails findByEmail(String email);
}