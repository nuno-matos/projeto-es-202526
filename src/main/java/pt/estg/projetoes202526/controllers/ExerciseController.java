package pt.estg.projetoes202526.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.estg.projetoes202526.domain.dto.ExerciseRegisterDTO;
import pt.estg.projetoes202526.domain.models.CourseUnit;
import pt.estg.projetoes202526.domain.models.Exercise;
import pt.estg.projetoes202526.domain.models.Question;
import pt.estg.projetoes202526.repositories.CourseUnitRepository;
import pt.estg.projetoes202526.repositories.ExerciseRepository;

import java.util.HashSet;

@RestController
@RequestMapping("api/exercise")
public class ExerciseController {

    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private CourseUnitRepository courseUnitRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createExercise(@RequestBody ExerciseRegisterDTO request) {
        CourseUnit courseUnit = courseUnitRepository.findByName(request.courseUnitName())
                .orElseThrow(() -> new RuntimeException("Course Unit not found"));

        Exercise exercise = new Exercise();
        exercise.setTitle(request.exerciseTitle());
        String description = "description";
        exercise.setDescription(description);

        request.questions().forEach(qDto -> {
            logger.info(
                    "Creating Question → group='{}', text='{}'",
                    qDto.group(),
                    qDto.questionText()
            );
            Question question = new Question();
            question.setQuestionText(qDto.questionText());
            question.setQuestionGroup(qDto.group());
            question.setExercise(exercise);

            exercise.getQuestions().add(question);
        });

        exerciseRepository.save(exercise);

        if (courseUnit.getExercises() == null) {
            courseUnit.setExercises(new HashSet<>());
        }
        courseUnit.getExercises().add(exercise);
        courseUnitRepository.save(courseUnit);

        logger.info("Exercise '{}' added to CourseUnit '{}'", exercise.getTitle(), courseUnit.getName());

        return ResponseEntity.ok().build();
    }
}