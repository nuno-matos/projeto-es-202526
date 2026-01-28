package pt.estg.projetoes202526.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import pt.estg.projetoes202526.domain.models.Role;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.repositories.RoleRepository;
import pt.estg.projetoes202526.repositories.UserRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

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
        saveUsersToDBIfNotExist("Chuck Norris","chuck@norris.pt", encryptedPassword, role);
    }

    private void saveUsersToDBIfNotExist(String name, String email ,String password, Role role) {
        if(userRepository.findByEmail(email) == null)
            userRepository.save(new User(name, email, password, role));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addRolesToDB();
        logger.info("Sucess on save roles to DB");
        addUsersToDB();
        logger.info("Sucess on save users to DB");
    }
}