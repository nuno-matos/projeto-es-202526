package pt.estg.projetoes202526.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.estg.projetoes202526.domain.dto.CourseUnitDTO;
import pt.estg.projetoes202526.domain.dto.ExerciseInfoDTO;
import pt.estg.projetoes202526.domain.dto.UserProfileResponseDTO;
import pt.estg.projetoes202526.domain.models.CourseUnit;
import pt.estg.projetoes202526.domain.models.Exercise;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.repositories.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user-info")
    public UserProfileResponseDTO getProfile(Principal principal) {
        String email = principal.getName();
        User user = (User) userRepository.findByEmail(email);

        List<CourseUnitDTO> courseUnitDTOs = new ArrayList<>();

        for (CourseUnit cu : user.getCourseUnits()) {
            List<ExerciseInfoDTO> exercises = new ArrayList<>();

            if (cu.getExercises() != null) {
                for (Exercise ex : cu.getExercises()) {
                    exercises.add(new ExerciseInfoDTO(ex.getId(), ex.getTitle()));
                }
            }

            courseUnitDTOs.add(new CourseUnitDTO(cu.getName(), exercises));
        }

        return new UserProfileResponseDTO(user.getName(), courseUnitDTOs);
    }
}