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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 🔐 Register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterDTO request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("User with email '" + request.getEmail() + "' already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRegisteredAt(LocalDate.now());
        user.setEnabled(true);
        user.setRole("USER");

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully: " + user.getEmail());
    }

    // 🔐 Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found with email: " + request.getEmail());
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid password for email: " + request.getEmail());
        }

        return ResponseEntity.ok("Login successful for: " + user.getEmail());
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
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found with ID: " + id);
        }
        String deletedEmail = userOpt.get().getEmail();
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted: " + deletedEmail);
    }
}