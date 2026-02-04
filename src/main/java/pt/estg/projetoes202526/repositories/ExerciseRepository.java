package pt.estg.projetoes202526.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.projetoes202526.domain.models.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {}