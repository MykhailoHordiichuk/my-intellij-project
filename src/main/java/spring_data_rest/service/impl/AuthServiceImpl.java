package spring_data_rest.service.impl;

import spring_data_rest.dao.UserRepository;
import spring_data_rest.dto.user.UserCreateDTO;
import spring_data_rest.dto.auth.LoginDTO;
import spring_data_rest.entity.User;
import spring_data_rest.service.AuthService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public String register(UserCreateDTO dto) {
        log.info("Registering new user with email={}", dto.getEmail());
        if (users.existsByEmail(dto.getEmail())) {
            log.warn("Attempt to register with already used email={}", dto.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setAge(dto.getAge());
        u.setPhoneNumber(dto.getPhoneNumber());

        users.save(u);
        log.info("User registered successfully with id={}", u.getId());
        return "ok";
    }

    @Override
    @Transactional(readOnly = true)
    public String login(LoginDTO dto) {
        log.info("Login attempt for email={}", dto.getEmail());
        User u = users.findByEmail(dto.getEmail()).orElseThrow(() -> {
            log.error("Login failed: user with email={} not found", dto.getEmail());
            return new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        });

        if (!encoder.matches(dto.getPassword(), u.getPassword())) {
            log.error("Login failed: invalid password for email={}", dto.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        log.info("User with email={} logged in successfully", dto.getEmail());
        return "ok";
    }
}