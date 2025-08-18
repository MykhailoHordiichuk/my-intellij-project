package spring_data_rest.dto.employee;

import lombok.*;
import jakarta.validation.constraints.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class EmployeeCreateDTO {

    @NotBlank @Size(max = 50)
    private String firstName;

    @NotBlank @Size(max = 50)
    private String lastName;

    @Email @NotBlank @Size(max = 100)
    private String email;

    @Size(max = 50)
    private String language;

    @Size(max = 20)
    private String level;

    @Min(0) @Max(80)
    private Integer experienceYears;

    @DecimalMin("0.0")
    private Double hourlyRate;
}