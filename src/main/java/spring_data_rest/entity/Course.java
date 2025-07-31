package spring_data_rest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "language")
    private String language;

    @Column(name = "level")
    private String level;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "durationWeeks")
    private int durationWeeks;

    @JsonManagedReference // teacher relation
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Employee teacher;

}