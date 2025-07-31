package spring_data_rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data //lombok library = auto getter, setter, toString
@NoArgsConstructor
@Entity
@Table(name = "users") // не "user", чтобы не конфликтовать с SQL
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private Integer age;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate registeredAt;

    @Column(nullable = false)
    private String role = "USER"; // USER / ADMIN / TEACHER

    private boolean enabled = true;
}