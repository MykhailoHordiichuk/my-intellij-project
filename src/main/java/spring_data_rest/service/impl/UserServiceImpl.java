package spring_data_rest.service.impl;

import spring_data_rest.dao.UserRepository;
import spring_data_rest.entity.User;
import spring_data_rest.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User create(User input) {
        log.info("Creating user with email={}", input.getEmail());
        if (input.getEmail() != null && repo.existsByEmail(input.getEmail())) {
            log.warn("Attempt to create user with already used email={}", input.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        User saved = repo.save(input);
        log.info("User created successfully with id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public User get(Integer id) {
        log.info("Fetching user id={}", id);
        return repo.findById(id).orElseThrow(() -> {
            log.error("User id={} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        log.info("Fetching all users");
        return repo.findAll();
    }

    @Override
    public User update(Integer id, User input) {
        log.info("Updating user id={}", id);
        User existing = get(id);
        BeanUtils.copyProperties(input, existing, "id", "password");
        User saved = repo.save(existing);
        log.info("User id={} updated successfully", id);
        return saved;
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting user id={}", id);
        if (!repo.existsById(id)) {
            log.error("Attempt to delete non-existing user id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repo.deleteById(id);
        log.info("User id={} deleted successfully", id);
    }
}