package pt.estg.projetoes202526.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.estg.projetoes202526.domain.models.CourseUnit;
import pt.estg.projetoes202526.domain.models.Exercise;
import pt.estg.projetoes202526.domain.models.Question;
import pt.estg.projetoes202526.domain.models.Role;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.repositories.CourseUnitRepository;
import pt.estg.projetoes202526.repositories.ExerciseRepository;
import pt.estg.projetoes202526.repositories.RoleRepository;
import pt.estg.projetoes202526.repositories.UserRepository;

import java.util.HashSet;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final CourseUnitRepository courseUnitRepository;
    private final ExerciseRepository exerciseRepository;

    public DataLoader(RoleRepository roleRepository, UserRepository userRepository, CourseUnitRepository courseUnitRepository, ExerciseRepository exerciseRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.courseUnitRepository = courseUnitRepository;
        this.exerciseRepository = exerciseRepository;
    }

    private void addRolesToDB() {
        saveRolesToDBIfNotExist("ADMIN");
        saveRolesToDBIfNotExist("STUDENT");
        saveRolesToDBIfNotExist("TEACHER");
    }

    private void saveRolesToDBIfNotExist(String name) {
        if(roleRepository.findByName(name).isEmpty())
            roleRepository.save(new Role(name));
    }

    private void addUsersToDB() {
        Role role = roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
        String encryptedPassword = new BCryptPasswordEncoder().encode("123");
        saveUsersToDBIfNotExist("John Wick","john@wick.pt", encryptedPassword, role);
        role = roleRepository.findByName("STUDENT").orElseThrow(() -> new RuntimeException("Role not found"));
        saveUsersToDBIfNotExist("John Rambo", "john@rambo.pt", encryptedPassword, role);
        saveUsersToDBIfNotExist("Jason Bourne", "jason@bourne.pt", encryptedPassword, role);
        role = roleRepository.findByName("TEACHER").orElseThrow(() -> new RuntimeException("Role not found"));
        saveUsersToDBIfNotExist("James Bond", "james@bond.pt", encryptedPassword, role);
    }

    private void saveUsersToDBIfNotExist(String name, String email ,String password, Role role) {
        if(userRepository.findByEmail(email) == null)
            userRepository.save(new User(name, email, password, role));
    }

    private void addCourseUnitsToDB() {
        saveCourseUnitsTODBIfNotExist("Technical English");
        saveCourseUnitsTODBIfNotExist("Introduction to Programming");
        saveCourseUnitsTODBIfNotExist("Mathematical Analysis");
        saveCourseUnitsTODBIfNotExist("Digital Systems");
        saveCourseUnitsTODBIfNotExist("Probability and Statistics");
        saveCourseUnitsTODBIfNotExist("Algorithms and Data Structures");
        saveCourseUnitsTODBIfNotExist("Computer Architecture");
        saveCourseUnitsTODBIfNotExist("Computational Physics ");
        saveCourseUnitsTODBIfNotExist("Software Engineering");
    }

    private void saveCourseUnitsTODBIfNotExist(String name) {
        if(courseUnitRepository.findByName(name).isEmpty())
            courseUnitRepository.save(new CourseUnit(name));
    }

    private void setUsersToCourseUnits() {
        User student = userRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("User not found"));
        User teacher = userRepository.findById(4)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CourseUnit programming = courseUnitRepository
                .findByName("Introduction to Programming")
                .orElseThrow();

        CourseUnit algorithms = courseUnitRepository
                .findByName("Algorithms and Data Structures")
                .orElseThrow();

        programming.getUsers().add(student);
        programming.getUsers().add(teacher);
        algorithms.getUsers().add(student);

        courseUnitRepository.save(programming);
        courseUnitRepository.save(algorithms);

        userRepository.save(student);
        userRepository.save(teacher);
    }

    private void createTestExercise() {
        if (exerciseRepository.count() > 0) return;

        CourseUnit programming = courseUnitRepository
                .findByName("Introduction to Programming")
                .orElseThrow();

        Exercise ex = new Exercise("Java Basics", "Exercício introdutório sobre classes e objetos.");

        Question q1 = new Question("Criar a classe Carro", "Grupo 1", ex);
        Question q2 = new Question("Definir atributos privados", "Grupo 1", ex);
        Question q3 = new Question("Implementar Getters e Setters", "Grupo 2", ex);

        ex.getQuestions().add(q1);
        ex.getQuestions().add(q2);
        ex.getQuestions().add(q3);

        exerciseRepository.save(ex);

        if (programming.getExercises() == null) {
            programming.setExercises(new HashSet<>());
        }
        programming.getExercises().add(ex);

        courseUnitRepository.save(programming);

        logger.info("Created test exercise: Java Basics with ID: " + ex.getId());
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        addRolesToDB();
        logger.info("Sucess on save roles to DB");
        addUsersToDB();
        logger.info("Sucess on save users to DB");
        addCourseUnitsToDB();
        logger.info("Sucess on save course units to DB");
        setUsersToCourseUnits();
        logger.info("Sucess on seting users to courseUnits");

        createTestExercise();
        logger.info("Success on creating test exercise");
    }
}