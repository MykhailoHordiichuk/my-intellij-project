package spring_data_rest.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class CourseCreateDTO {
    @NotBlank
    private String language;

    private String level;

    @Size(max = 2000)
    private String description;

    @DecimalMin("0.0")
    private double price;

    @Min(0)
    private int durationWeeks;

    private Integer teacherId; // опционально при создании
}