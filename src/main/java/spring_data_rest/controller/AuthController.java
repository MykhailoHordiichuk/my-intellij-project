package spring_data_rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spring_data_rest.dto.UserRegisterDTO;
import spring_data_rest.dto.UserLoginDTO;
import spring_data_rest.entity.User;
import spring_data_rest.dao.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO request) {
        logger.info("POST /api/auth/register called with email: {}", request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            logger.warn("Registration failed: user with email {} already exists", request.getEmail());
            return ResponseEntity.badRequest().body("Пользователь с таким email уже существует");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setFullName(request.getFullName());
        newUser.setEnabled(true);
        newUser.setRole("USER");
        newUser.setPassword(passwordEncoder.encode(request.getPassword())); // шифрование пароля

        userRepository.save(newUser);
        logger.info("User {} successfully registered", request.getUsername());

        return ResponseEntity.ok("Пользователь успешно зарегистрирован");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO request) {
        logger.info("POST /api/auth/login called with email: {}", request.getEmail());

        return userRepository.findByEmail(request.getEmail())
                .map(user -> {
                    if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                        logger.info("Successful login for user: {}", request.getEmail());
                        return ResponseEntity.ok("Успешный вход");
                    } else {
                        logger.warn("Invalid password for user: {}", request.getEmail());
                        return ResponseEntity.status(401).body("Неверный пароль");
                    }
                })
                .orElseGet(() -> {
                    logger.warn("User with email {} not found", request.getEmail());
                    return ResponseEntity.status(404).body("Пользователь не найден");
                });
    }
}