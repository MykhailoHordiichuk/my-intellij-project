package spring_data_rest.controller;

import spring_data_rest.dto.employee.*;
import spring_data_rest.entity.Employee;
import spring_data_rest.service.EmployeeService;
import spring_data_rest.entity.Course;
import spring_data_rest.dto.course.CourseDTO;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employees;

    public EmployeeController(EmployeeService employees) {
        this.employees = employees;
    }

    @PostMapping
    public EmployeeDTO create(@Valid @RequestBody EmployeeCreateDTO dto) {
        Employee saved = employees.create(fromCreate(dto));
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public EmployeeDTO get(@PathVariable Integer id) {
        return toDto(employees.get(id));
    }

    @GetMapping
    public List<EmployeeDTO> getAll() {
        return employees.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public EmployeeDTO update(@PathVariable Integer id, @Valid @RequestBody EmployeeUpdateDTO dto) {
        Employee current = employees.get(id);
        applyUpdate(current, dto);
        Employee saved = employees.update(id, current);
        return toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        employees.delete(id);
    }

    /* ---------- mapping ---------- */

    private EmployeeDTO toDto(Employee e) {
        return new EmployeeDTO(
                e.getId(),
                e.getFirstName(),
                e.getLastName(),
                e.getEmail(),
                e.getLanguage(),
                e.getLevel(),
                e.getExperienceYears(),
                e.getHourlyRate()
        );
    }

    private Employee fromCreate(EmployeeCreateDTO dto) {
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setLanguage(dto.getLanguage());
        e.setLevel(dto.getLevel());
        if (dto.getExperienceYears() != null) e.setExperienceYears(dto.getExperienceYears());
        if (dto.getHourlyRate() != null) e.setHourlyRate(dto.getHourlyRate());
        return e;
    }

    private void applyUpdate(Employee e, EmployeeUpdateDTO dto) {
        if (dto.getFirstName() != null)      e.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)       e.setLastName(dto.getLastName());
        if (dto.getEmail() != null)          e.setEmail(dto.getEmail());
        if (dto.getLanguage() != null)       e.setLanguage(dto.getLanguage());
        if (dto.getLevel() != null)          e.setLevel(dto.getLevel());
        if (dto.getExperienceYears() != null)e.setExperienceYears(dto.getExperienceYears());
        if (dto.getHourlyRate() != null)     e.setHourlyRate(dto.getHourlyRate());
    }
    // NEW: get courses of employee
    @GetMapping("/{id}/courses")
    public List<CourseDTO> getCourses(@PathVariable Integer id) {
        return employees.getCourses(id).stream().map(this::toCourseDto).toList();
    }

    // NEW: assign course to employee
    @PostMapping("/{id}/courses/{courseId}")
    public void assignCourse(@PathVariable Integer id, @PathVariable Integer courseId) {
        employees.assignCourse(id, courseId);
    }

    // NEW: remove course from employee
    @DeleteMapping("/{id}/courses/{courseId}")
    public void removeCourse(@PathVariable Integer id, @PathVariable Integer courseId) {
        employees.removeCourse(id, courseId);
    }

    private CourseDTO toCourseDto(Course c) {
        CourseDTO dto = new CourseDTO();
        dto.setId(c.getId());
        dto.setLanguage(c.getLanguage());
        dto.setLevel(c.getLevel());
        dto.setDescription(c.getDescription());
        dto.setPrice(c.getPrice());
        dto.setDurationWeeks(c.getDurationWeeks());
        dto.setTeacherId(c.getTeacher() != null ? c.getTeacher().getId() : null);
        return dto;
    }
}