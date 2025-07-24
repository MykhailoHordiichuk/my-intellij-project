package spring_data_rest.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

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

    // 🔥 Removed: List<Student> students;

    public Course() {}

    public Course(String language, String level, String description, double price, int durationWeeks, Employee teacher) {
        this.language = language;
        this.level = level;
        this.description = description;
        this.price = price;
        this.durationWeeks = durationWeeks;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public Employee getTeacher() {
        return teacher;
    }

    public void setTeacher(Employee teacher) {
        this.teacher = teacher;
    }
}