package spring_data_rest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false, length = 100)
    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    @Column(length = 50)
    @Size(max = 50)
    private String firstName;

    @Column(length = 50)
    @Size(max = 50)
    private String lastName;

    @Min(1)
    @Max(99)
    private Integer age;

    @Column(length = 20)
    @Size(max = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    @PrePersist
    public void onCreate() {
        if (registeredAt == null) {
            registeredAt = LocalDateTime.now();
        }
    }
}