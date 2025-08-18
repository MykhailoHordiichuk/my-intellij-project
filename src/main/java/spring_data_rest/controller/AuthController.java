package spring_data_rest.controller;

import spring_data_rest.dto.user.UserCreateDTO;
import spring_data_rest.dto.auth.LoginDTO;
import spring_data_rest.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UserCreateDTO dto) {
        return auth.register(dto);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDTO dto) {
        return auth.login(dto);
    }
}