package spring_data_rest.controller;

import spring_data_rest.dto.course.*;
import spring_data_rest.entity.Course;
import spring_data_rest.entity.Employee;
import spring_data_rest.service.CourseService;
import spring_data_rest.service.EmployeeService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courses;
    private final EmployeeService employees;

    public CourseController(CourseService courses, EmployeeService employees) {
        this.courses = courses;
        this.employees = employees;
    }

    @PostMapping
    public CourseDTO create(@Valid @RequestBody CourseCreateDTO dto) {
        Course saved = courses.create(fromCreate(dto));
        // teacherId опционально: если задан, подцепим преподавателя
        if (dto.getTeacherId() != null) {
            Employee teacher = employees.get(dto.getTeacherId());
            saved.setTeacher(teacher);
            saved = courses.update(saved.getId(), saved);
        }
        return toDto(saved);
    }

    @GetMapping("/{id}")
    public CourseDTO get(@PathVariable Integer id) {
        return toDto(courses.get(id));
    }

    @GetMapping
    public List<CourseDTO> getAll() {
        return courses.getAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public CourseDTO update(@PathVariable Integer id, @Valid @RequestBody CourseUpdateDTO dto) {
        Course current = courses.get(id);
        applyUpdate(current, dto);
        // если приходит новый teacherId — подменим преподавателя
        if (dto.getTeacherId() != null) {
            current.setTeacher(employees.get(dto.getTeacherId()));
        }
        Course saved = courses.update(id, current);
        return toDto(saved);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        courses.delete(id);
    }

    /* -------- mapping helpers -------- */

    private CourseDTO toDto(Course c) {
        return new CourseDTO(
                c.getId(),
                c.getLanguage(),
                c.getLevel(),
                c.getDescription(),
                c.getPrice(),
                c.getDurationWeeks(),
                c.getTeacher() != null ? c.getTeacher().getId() : null
        );
    }

    private Course fromCreate(CourseCreateDTO dto) {
        Course c = new Course();
        c.setLanguage(dto.getLanguage());
        c.setLevel(dto.getLevel());
        c.setDescription(dto.getDescription());
        c.setPrice(dto.getPrice());
        c.setDurationWeeks(dto.getDurationWeeks());
        // teacher назначаем отдельно (см. выше), чтобы не тащить бизнес-логику сюда
        return c;
    }

    private void applyUpdate(Course c, CourseUpdateDTO dto) {
        if (dto.getLanguage() != null)       c.setLanguage(dto.getLanguage());
        if (dto.getLevel() != null)          c.setLevel(dto.getLevel());
        if (dto.getDescription() != null)    c.setDescription(dto.getDescription());
        if (dto.getPrice() != null)          c.setPrice(dto.getPrice());
        if (dto.getDurationWeeks() != null)  c.setDurationWeeks(dto.getDurationWeeks());
        // teacherId обрабатываем выше через EmployeeService
    }
}