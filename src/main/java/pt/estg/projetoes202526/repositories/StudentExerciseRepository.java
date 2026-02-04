package pt.estg.projetoes202526.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.estg.projetoes202526.domain.models.Exercise;
import pt.estg.projetoes202526.domain.models.StudentExercise;
import pt.estg.projetoes202526.domain.models.User;

import java.util.List;
import java.util.Optional;

public interface StudentExerciseRepository extends JpaRepository<StudentExercise, Integer> {
    Optional<StudentExercise> findByStudentAndExercise(User student, Exercise exercise);
    List<StudentExercise> findByExercise(Exercise exercise);
    List<StudentExercise> findByStudent(User student);
}