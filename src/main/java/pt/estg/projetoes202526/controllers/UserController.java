package pt.estg.projetoes202526.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.estg.projetoes202526.domain.dto.UserProfileResponseDTO;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.domain.models.CourseUnit;
import pt.estg.projetoes202526.repositories.CourseUnitRepository;
import pt.estg.projetoes202526.repositories.UserRepository;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseUnitRepository courseUnitRepository;

    @GetMapping("/user-info")
    public UserProfileResponseDTO getProfile(Principal principal) {

        String email = principal.getName(); // JWT sub
        User user = (User) userRepository.findByEmail(email);
        String name = user.getName();

        logger.info("Email: {}", email);
        logger.info("Name: {}", name);
        List<String> courseUnits = user.getCourseUnits()
                .stream()
                .map(CourseUnit::getName)
                .toList();
        logger.info("CourseUnits: {}", courseUnits);

        return new UserProfileResponseDTO(name, courseUnits);
    }
}
