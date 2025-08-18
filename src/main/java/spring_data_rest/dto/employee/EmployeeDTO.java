package spring_data_rest.dto.employee;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class EmployeeDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String language;
    private String level;
    private int experienceYears;
    private double hourlyRate;
}