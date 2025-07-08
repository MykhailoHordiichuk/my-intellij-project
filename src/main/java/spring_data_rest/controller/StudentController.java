package spring_data_rest.controller;

import spring_data_rest.dao.CourseRepository;
import spring_data_rest.dao.StudentRepository;
import spring_data_rest.entity.Course;
import spring_data_rest.entity.Student;
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

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    // Получить всех студентов
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return ResponseEntity.ok(students);
    }

    // Получить одного студента по ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Добавить нового студента
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student saved = studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Обновить существующего студента
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Student student = optionalStudent.get();
        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setAge(updatedStudent.getAge());
        student.setEmail(updatedStudent.getEmail());
        student.setPhoneNumber(updatedStudent.getPhoneNumber());
        student.setRegisteredAt(updatedStudent.getRegisteredAt());
        student.setCourses(updatedStudent.getCourses()); // необязательная замена

        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }

    // Удалить студента
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable int id) {
        if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student with ID = " + id + " not found");
        }
        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student with ID = " + id + " was deleted");
    }

    // Привязать студента к курсу
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> registerStudentToCourse(@PathVariable int studentId, @PathVariable int courseId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalStudent.isEmpty() || optionalCourse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Student student = optionalStudent.get();
        Course course = optionalCourse.get();

        if (student.getCourses() == null) {
            student.setCourses(new ArrayList<>());
        }

        if (!student.getCourses().contains(course)) {
            student.getCourses().add(course);
        }

        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(saved);
    }
}