package spring_data_rest.dto.course;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class CourseDTO {
    private Integer id;
    private String language;
    private String level;
    private String description;
    private double price;
    private int durationWeeks;
    private Integer teacherId; // Employee.id
}