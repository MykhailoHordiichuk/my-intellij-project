package spring_data_rest.controller;

import spring_data_rest.dto.user.UserCreateDTO;
import spring_data_rest.dto.user.UserDTO;
import spring_data_rest.dto.user.UserUpdateDTO;
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

    public UserController(UserService users) {
        this.users = users;
    }

    // CREATE
    @PostMapping
    public UserDTO create(@Valid @RequestBody UserCreateDTO dto) {
        User toSave = fromCreateDto(dto);
        User saved = users.create(toSave);
        return toDto(saved);
    }

    // READ ONE
    @GetMapping("/{id}")
    public UserDTO get(@PathVariable Integer id) {
        return toDto(users.get(id));
    }

    // READ ALL
    @GetMapping
    public List<UserDTO> getAll() {
        return users.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    // UPDATE (частичное — применяем только не-null поля из DTO)
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Integer id, @Valid @RequestBody UserUpdateDTO dto) {
        User current = users.get(id);
        applyUpdate(current, dto);
        User saved = users.update(id, current);
        return toDto(saved);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        users.delete(id);
    }

    /* ---------- mapping helpers ---------- */

    private UserDTO toDto(User u) {
        UserDTO dto = new UserDTO();
        dto.setId(u.getId());
        dto.setEmail(u.getEmail());
        dto.setFirstName(u.getFirstName());
        dto.setLastName(u.getLastName());
        dto.setAge(u.getAge());
        dto.setPhoneNumber(u.getPhoneNumber());
        dto.setRegisteredAt(u.getRegisteredAt());
        return dto;
    }

    private User fromCreateDto(UserCreateDTO dto) {
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword()); // если шифруешь в сервисе — оставь так; если тут — зашифруй здесь
        u.setFirstName(dto.getFirstName());
        u.setLastName(dto.getLastName());
        u.setAge(dto.getAge());
        u.setPhoneNumber(dto.getPhoneNumber());
        u.setRegisteredAt(dto.getRegisteredAt());
        return u;
    }

    private void applyUpdate(User u, UserUpdateDTO dto) {
        if (dto.getEmail() != null)       u.setEmail(dto.getEmail());
        if (dto.getFirstName() != null)   u.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)    u.setLastName(dto.getLastName());
        if (dto.getAge() != null)         u.setAge(dto.getAge());
        if (dto.getPhoneNumber() != null) u.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getRegisteredAt() != null)u.setRegisteredAt(dto.getRegisteredAt());
    }
}