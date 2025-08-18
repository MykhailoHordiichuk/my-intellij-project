package spring_data_rest.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class CourseUpdateDTO {
    private String language;
    private String level;

    @Size(max = 2000)
    private String description;

    @DecimalMin("0.0")
    private Double price;          // обёртки → поле опционально

    @Min(0)
    private Integer durationWeeks;

    private Integer teacherId;     // поменять учителя (опционально)
}