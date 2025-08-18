package spring_data_rest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "courses")
@ToString(exclude = "courses")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Size(max = 50)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;

    @Column(length = 50)
    @Size(max = 50)
    private String language;

    @Column(length = 20)
    @Size(max = 20)
    private String level;

    @Min(0)
    @Max(80)
    private int experienceYears;

    @DecimalMin(value = "0.0", inclusive = true)
    private double hourlyRate;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Course> courses = new ArrayList<>();

}