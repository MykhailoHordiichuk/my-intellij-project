package spring_data_rest.controller;

import spring_data_rest.dao.CourseRepository;
import spring_data_rest.dao.EmployeeRepository;
import spring_data_rest.entity.Course;
import spring_data_rest.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Получить все курсы
    @GetMapping
    public List<Course> getAllCourses() {
        logger.info("GET /api/courses - getAllCourses called");
        List<Course> courses = courseRepository.findAll();
        logger.debug("Retrieved {} courses", courses.size());
        return courses;
    }

    // Получить курс по ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        logger.info("GET /api/courses/{} - getCourseById called", id);
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isPresent()) {
            logger.debug("Course found: {}", courseOpt.get());
            return ResponseEntity.ok(courseOpt.get());
        } else {
            logger.warn("Course with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Добавить новый курс
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        logger.info("POST /api/courses - addCourse called with data: {}", course);

        if (course.getTeacher() != null) {
            int teacherId = course.getTeacher().getId();
            logger.debug("Checking if teacher with ID {} exists", teacherId);
            Optional<Employee> teacher = employeeRepository.findById(teacherId);
            if (teacher.isEmpty()) {
                logger.warn("Teacher with ID {} not found", teacherId);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Teacher with ID = " + teacherId + " not found");
            }
            course.setTeacher(teacher.get());
        }

        Course savedCourse = courseRepository.save(course);
        logger.info("Course saved with ID {}", savedCourse.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    // Обновить курс по ID
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateCourse(@PathVariable int id, @RequestBody Course updatedCourse) {
        logger.info("PUT /api/courses/{} - updateCourse called", id);
        Optional<Course> existingOpt = courseRepository.findById(id);
        if (existingOpt.isEmpty()) {
            logger.warn("Course with ID {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID = " + id + " not found");
        }

        Course course = existingOpt.get();
        logger.debug("Existing course before update: {}", course);

        course.setLanguage(updatedCourse.getLanguage());
        course.setLevel(updatedCourse.getLevel());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());
        course.setDurationWeeks(updatedCourse.getDurationWeeks());

        if (updatedCourse.getTeacher() != null) {
            int teacherId = updatedCourse.getTeacher().getId();
            Optional<Employee> teacher = employeeRepository.findById(teacherId);
            teacher.ifPresent(course::setTeacher);
            logger.debug("Updated teacher with ID {}", teacherId);
        }

        Course saved = courseRepository.save(course);
        logger.info("Course with ID {} updated", saved.getId());
        return ResponseEntity.ok(saved);
    }

    // Удалить курс по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable int id) {
        logger.info("DELETE /api/courses/{} - deleteCourse called", id);

        if (!courseRepository.existsById(id)) {
            logger.warn("Course with ID {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Course with ID = " + id + " not found");
        }

        courseRepository.deleteById(id);
        logger.info("Course with ID {} deleted", id);
        return ResponseEntity.ok("Course with ID = " + id + " was deleted");
    }
}