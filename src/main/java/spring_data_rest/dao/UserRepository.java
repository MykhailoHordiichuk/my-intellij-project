package spring_data_rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_data_rest.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // для проверки уникальности email при регистрации
    boolean existsByEmail(String email);

    // для логина по email
    Optional<User> findByEmail(String email);
}