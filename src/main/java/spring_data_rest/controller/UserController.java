package spring_data_rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring_data_rest.dao.UserRepository;
import spring_data_rest.dto.UserLoginDTO;
import spring_data_rest.dto.UserRegisterDTO;
import spring_data_rest.entity.User;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔐 Register
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegisterDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "User with email already exists",
                    "email", request.getEmail()
            ));
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAge(request.getAge());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRegisteredAt(LocalDate.now());
        user.setEnabled(true);
        user.setRole("USER");

        userRepository.save(user);

        //примечание в return password - user.getPassword() возвращается "хэшированым" в JSON
        return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "email", user.getEmail(),
                "password", user.getPassword(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "age", String.valueOf(user.getAge()),
                "phoneNumber", user.getPhoneNumber()
        ));
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "User not found",
                    "email", request.getEmail()
            ));
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "Invalid password",
                    "email", request.getEmail()
            ));
        }
        //пароль так же возвращается "хэшированым"
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "email", user.getEmail(),
                "password", user.getPassword(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "age", String.valueOf(user.getAge()),
                "phoneNumber", user.getPhoneNumber()
        ));
    }

    // 📥 Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 📥 Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✏️ Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody User updated) {
        return userRepository.findById(id).map(user -> {
            user.setFirstName(updated.getFirstName());
            user.setLastName(updated.getLastName());
            user.setAge(updated.getAge());
            user.setPhoneNumber(updated.getPhoneNumber());
            user.setRegisteredAt(updated.getRegisteredAt());
            user.setRole(updated.getRole());
            user.setEnabled(updated.isEnabled());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ❌ Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable int id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of(
                    "error", "User not found",
                    "id", String.valueOf(id)
            ));
        }
        String deletedEmail = userOpt.get().getEmail();
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of(
                "message", "User deleted",
                "email", deletedEmail
        ));
    }
}