package spring_data_rest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;

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

    // ===== Getters & Setters =====

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public Integer getAge() { return age; }

    public void setAge(Integer age) { this.age = age; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public LocalDate getRegisteredAt() { return registeredAt; }

    public void setRegisteredAt(LocalDate registeredAt) { this.registeredAt = registeredAt; }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
}