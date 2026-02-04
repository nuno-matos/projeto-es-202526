package pt.estg.projetoes202526.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.estg.projetoes202526.domain.dto.StudentProgressDTO;
import pt.estg.projetoes202526.domain.models.Exercise;
import pt.estg.projetoes202526.domain.models.StudentExercise;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.repositories.ExerciseRepository;
import pt.estg.projetoes202526.repositories.StudentExerciseRepository;
import pt.estg.projetoes202526.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/progress")
public class ProgressController {

    @Autowired
    private StudentExerciseRepository studentExerciseRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/teacher/{exerciseId}")
    public ResponseEntity<List<StudentProgressDTO>> getExerciseProgress(@PathVariable Integer exerciseId) {
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);
        if (exercise == null) return ResponseEntity.notFound().build();

        List<StudentExercise> progressList = studentExerciseRepository.findByExercise(exercise);
        List<StudentProgressDTO> dtos = new ArrayList<>();

        for (StudentExercise se : progressList) {
            dtos.add(new StudentProgressDTO(
                    se.getId(),
                    se.getStudent().getName(),
                    se.getCurrentPhase(),
                    se.getTotalPhases(),
                    se.isHelpRequested(),
                    se.isCompleted(),
                    se.getGrade()
            ));
        }
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/student/{exerciseId}")
    public ResponseEntity<StudentProgressDTO> getMyProgress(@PathVariable Integer exerciseId, Principal principal) {
        String email = principal.getName();
        User student = (User) userRepository.findByEmail(email);
        Exercise exercise = exerciseRepository.findById(exerciseId).orElse(null);

        if (student == null || exercise == null) return ResponseEntity.badRequest().build();

        StudentExercise se = studentExerciseRepository.findByStudentAndExercise(student, exercise)
                .orElseGet(() -> {
                    StudentExercise newSe = new StudentExercise(student, exercise, exercise.getQuestions().size());
                    return studentExerciseRepository.save(newSe);
                });

        return ResponseEntity.ok(new StudentProgressDTO(
                se.getId(), student.getName(), se.getCurrentPhase(), se.getTotalPhases(),
                se.isHelpRequested(), se.isCompleted(), se.getGrade()
        ));
    }

    @PostMapping("/{id}/help")
    public ResponseEntity<?> toggleHelp(@PathVariable Integer id) {
        Optional<StudentExercise> seOpt = studentExerciseRepository.findById(id);
        if (seOpt.isEmpty()) return ResponseEntity.notFound().build();

        StudentExercise se = seOpt.get();
        se.setHelpRequested(!se.isHelpRequested());
        studentExerciseRepository.save(se);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/next")
    public ResponseEntity<?> nextPhase(@PathVariable Integer id) {
        Optional<StudentExercise> seOpt = studentExerciseRepository.findById(id);
        if (seOpt.isEmpty()) return ResponseEntity.notFound().build();

        StudentExercise se = seOpt.get();
        if (se.getCurrentPhase() < se.getTotalPhases()) {
            se.setCurrentPhase(se.getCurrentPhase() + 1);
            if (se.getCurrentPhase().equals(se.getTotalPhases())) {
                se.setCompleted(true);
            }
            studentExerciseRepository.save(se);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/grade")
    public ResponseEntity<?> gradeStudent(@PathVariable Integer id, @RequestBody Double grade) {
        Optional<StudentExercise> seOpt = studentExerciseRepository.findById(id);
        if (seOpt.isEmpty()) return ResponseEntity.notFound().build();

        StudentExercise se = seOpt.get();
        se.setGrade(grade);
        se.setCompleted(true);
        se.setHelpRequested(false);
        studentExerciseRepository.save(se);

        return ResponseEntity.ok().build();
    }
}