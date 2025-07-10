package spring_data_rest.controller;

import spring_data_rest.dao.CourseRepository;
import spring_data_rest.dao.StudentRepository;
import spring_data_rest.entity.Course;
import spring_data_rest.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Получить всех студентов
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        logger.info("GET /api/students - getAllStudents called");
        List<Student> students = studentRepository.findAll();
        logger.debug("Found {} student(s)", students.size());
        return ResponseEntity.ok(students);
    }

    // Получить одного студента по ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        logger.info("GET /api/students/{} - getStudentById called", id);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            logger.debug("Student found: {}", optionalStudent.get());
            return ResponseEntity.ok(optionalStudent.get());
        } else {
            logger.warn("Student with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Добавить нового студента
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        logger.info("POST /api/students - addStudent called");
        logger.debug("Student to add: {}", student);
        Student saved = studentRepository.save(student);
        logger.info("Student created with ID {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Обновить существующего студента
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        logger.info("PUT /api/students/{} - updateStudent called", id);
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            logger.warn("Student with ID {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Student student = optionalStudent.get();
        logger.debug("Original student data: {}", student);
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setAge(updatedStudent.getAge());
        student.setEmail(updatedStudent.getEmail());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setRegisteredAt(updatedStudent.getRegisteredAt());
        student.setCourses(updatedStudent.getCourses());

        Student saved = studentRepository.save(student);
        logger.info("Student with ID {} updated", id);
        return ResponseEntity.ok(saved);
    }

    // Удалить студента
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable int id) {
        logger.info("DELETE /api/students/{} - deleteStudentById called", id);
        if (!studentRepository.existsById(id)) {
            logger.warn("Student with ID {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with ID = " + id + " not found");
        }
        studentRepository.deleteById(id);
        logger.info("Student with ID {} deleted", id);
        return ResponseEntity.ok("Student with ID = " + id + " was deleted");
    }

    // Привязать студента к курсу
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> registerStudentToCourse(@PathVariable int studentId, @PathVariable int courseId) {
        logger.info("POST /api/students/{}/courses/{} - registerStudentToCourse called", studentId, courseId);

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isEmpty() || optionalCourse.isEmpty()) {
            logger.warn("Student or Course not found: studentId={}, courseId={}", studentId, courseId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Student student = optionalStudent.get();
        Course course = optionalCourse.get();

        if (student.getCourses() == null) {
            student.setCourses(new ArrayList<>());
        }

        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
            logger.debug("Student ID {} registered to course ID {}", studentId, courseId);
        } else {
            logger.debug("Student ID {} already registered to course ID {}", studentId, courseId);
        }

        Student saved = studentRepository.save(student);
        logger.info("Student ID {} successfully saved after course registration", saved.getId());
        return ResponseEntity.ok(saved);
    }
}