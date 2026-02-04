package pt.estg.projetoes202526.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.projetoes202526.domain.models.CourseUnit;

import java.util.Optional;

public interface CourseUnitRepository extends JpaRepository<CourseUnit, Integer> {
    Optional<CourseUnit> findByName(String name);
    Optional<CourseUnit> findById(Integer id);
}
