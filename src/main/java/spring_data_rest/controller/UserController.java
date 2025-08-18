package spring_data_rest.controller;

import spring_data_rest.dto.user.*;
import spring_data_rest.entity.User;
import spring_data_rest.service.UserService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService users;

    public UserController(UserService users) { this.users = users; }

    @PostMapping
    public UserDTO create(@Valid @RequestBody UserCreateDTO dto) {
        User toSave = fromCreateDto(dto);
        User saved = users.create(toSave);
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Integer id) {
        return toDto(users.get(id));
    }

    @GetMapping
    public List<UserDTO> getAll() {
        return users.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id, @Valid @RequestBody UserUpdateDTO dto) {
        User current = users.get(id);
        applyUpdate(current, dto);
        User saved = users.update(id, current);
        return toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        users.delete(id);
    }

    /* ---------- mapping ---------- */

    private UserDTO toDto(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setEmail(u.getEmail());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setAge(u.getAge());
        dto.setPhoneNumber(u.getPhoneNumber());
        dto.setRegisteredAt(u.getRegisteredAt()); // LocalDateTime -> LocalDateTime
        return dto;
    }

    private User fromCreateDto(UserCreateDTO dto) {
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword()); // шифруется в AuthServiceImpl
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setAge(dto.getAge());
        u.setPhoneNumber(dto.getPhoneNumber());
        // НЕ трогаем registeredAt — проставит @PrePersist
        return u;
    }

    private void applyUpdate(User u, UserUpdateDTO dto) {
        if (dto.getEmail() != null)       u.setEmail(dto.getEmail());
        if (dto.getFirstName() != null)   u.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)    u.setLastName(dto.getLastName());
        if (dto.getAge() != null)         u.setAge(dto.getAge());
        if (dto.getPhoneNumber() != null) u.setPhoneNumber(dto.getPhoneNumber());
        // registeredAt — read-only
    }
}