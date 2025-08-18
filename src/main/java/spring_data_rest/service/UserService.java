package spring_data_rest.service;

import spring_data_rest.entity.User;
import java.util.List;

public interface UserService {
    User create(User input);
    User get(Integer id);
    List<User> getAll();
    User update(Integer id, User input);
    void delete(Integer id);
}