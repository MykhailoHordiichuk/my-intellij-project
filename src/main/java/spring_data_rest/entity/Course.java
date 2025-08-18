package spring_data_rest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Data @NoArgsConstructor @AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    @NotBlank @Size(max = 50)
    private String language;

    @Column(length = 20)
    @Size(max = 20)
    private String level;

    @Column(length = 2000)
    @Size(max = 2000)
    private String description;

    @DecimalMin("0.0")
    private double price;

    @Min(0)
    private int durationWeeks;

    // many courses → one teacher (Employee)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id") // nullable: курс может быть без преподавателя
    private Employee teacher;
}