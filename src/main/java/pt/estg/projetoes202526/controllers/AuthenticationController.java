package pt.estg.projetoes202526.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pt.estg.projetoes202526.domain.dto.LoginDTO;
import pt.estg.projetoes202526.domain.dto.LoginResponseDTO;
import pt.estg.projetoes202526.domain.dto.RegisterDTO;
import pt.estg.projetoes202526.domain.models.Role;
import pt.estg.projetoes202526.domain.models.User;
import pt.estg.projetoes202526.repositories.RoleRepository;
import pt.estg.projetoes202526.repositories.UserRepository;
import pt.estg.projetoes202526.security.TokenService;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO request) {
        if (userRepository.findByEmail(request.email()) != null)
            return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        Role role = roleRepository.findByName(request.role()).orElseThrow(() -> new RuntimeException("Role not found"));
        User user = new User(request.name(), request.email(), encryptedPassword, role);
        userRepository.save(user);
        logger.info("SAVED USER: {}", user.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO request) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        logger.info(usernamePassword.toString());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) authentication.getPrincipal());
        User user = (User) userRepository.findByEmail(request.email());
        logger.info("LOGIN USER: {}", user.getEmail());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}