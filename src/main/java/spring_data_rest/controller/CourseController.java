package spring_data_rest.controller;

import spring_data_rest.dao.CourseRepository;
import spring_data_rest.dao.EmployeeRepository;
import spring_data_rest.entity.Course;
import spring_data_rest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Получить все курсы
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Получить курс по ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        return courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Добавить новый курс (POST /api/courses)
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        if (course.getTeacher() != null) {
            int teacherId = course.getTeacher().getId();
            Optional<Employee> teacher = employeeRepository.findById(teacherId);
            if (teacher.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Teacher with ID = " + teacherId + " not found");
            }
            course.setTeacher(teacher.get());
        }
        Course savedCourse = courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    // Обновить курс по ID
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        Optional<Course> existingOpt = courseRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID = " + id + " not found");
        }

        Course course = existingOpt.get();
        course.setLanguage(updatedCourse.getLanguage());
        course.setLevel(updatedCourse.getLevel());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());
        course.setDurationWeeks(updatedCourse.getDurationWeeks());

        if (updatedCourse.getTeacher() != null) {
            Optional<Employee> teacher = employeeRepository.findById(updatedCourse.getTeacher().getId());
            teacher.ifPresent(course::setTeacher);
        }

        Course saved = courseRepository.save(course);
        return ResponseEntity.ok(saved);
    }

    // Удалить курс по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        if (!courseRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID = " + id + " not found");
        }

        courseRepository.deleteById(id);
        return ResponseEntity.ok("Course with ID = " + id + " was deleted");
    }
}