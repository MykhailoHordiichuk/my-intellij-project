package spring_data_rest.service;

import spring_data_rest.dto.user.UserCreateDTO;
import spring_data_rest.dto.auth.LoginDTO;

public interface AuthService {
    String register(UserCreateDTO dto);
    String login(LoginDTO dto);
}