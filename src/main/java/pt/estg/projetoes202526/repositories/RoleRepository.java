package pt.estg.projetoes202526.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.projetoes202526.domain.models.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
}