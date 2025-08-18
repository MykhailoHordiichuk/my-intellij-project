package spring_data_rest.service.impl;

import spring_data_rest.dao.EmployeeRepository;
import spring_data_rest.dao.CourseRepository;
import spring_data_rest.entity.Employee;
import spring_data_rest.entity.Course;
import spring_data_rest.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repo;
    private final CourseRepository courseRepo;

    public EmployeeServiceImpl(EmployeeRepository repo, CourseRepository courseRepo) {
        this.repo = repo;
        this.courseRepo = courseRepo;
    }

    @Override
    public Employee create(Employee input) {
        log.info("Creating employee with email={}", input.getEmail());
        if (repo.existsByEmail(input.getEmail())) {
            log.warn("Attempt to create employee with already used email={}", input.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }
        Employee saved = repo.save(input);
        log.info("Employee created successfully with id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Employee get(Integer id) {
        log.info("Fetching employee with id={}", id);
        return repo.findById(id).orElseThrow(
                () -> {
                    log.error("Employee with id={} not found", id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
                }
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAll() {
        log.info("Fetching all employees");
        return repo.findAll();
    }

    @Override
    public Employee update(Integer id, Employee input) {
        log.info("Updating employee with id={}", id);
        Employee existing = get(id);

        if (!existing.getEmail().equals(input.getEmail())
                && repo.existsByEmail(input.getEmail())) {
            log.warn("Attempt to update employee id={} with already used email={}", id, input.getEmail());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        // do not touch id/courses in generic update
        BeanUtils.copyProperties(input, existing, "id", "courses");
        Employee saved = repo.save(existing);
        log.info("Employee with id={} updated successfully", id);
        return saved;
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting employee with id={}", id);
        if (!repo.existsById(id)) {
            log.error("Attempt to delete non-existing employee with id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        repo.deleteById(id);
        log.info("Employee with id={} deleted successfully", id);
    }

    // --------- NEW: courses management ---------

    @Override
    public void assignCourse(Integer employeeId, Integer courseId) {
        log.info("Assigning course id={} to employee id={}", courseId, employeeId);
        Employee employee = get(employeeId); // ensures existence

        Course course = courseRepo.findById(courseId).orElseThrow(() -> {
            log.error("Course with id={} not found", courseId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        });

        if (course.getTeacher() != null && course.getTeacher().getId() == employeeId) {
            log.info("Course id={} is already assigned to employee id={}, skipping", courseId, employeeId);
            return; // idempotent
        }

        course.setTeacher(employee); // reassign allowed
        courseRepo.save(course);
        log.info("Course id={} assigned to employee id={} successfully", courseId, employeeId);
    }

    @Override
    public void removeCourse(Integer employeeId, Integer courseId) {
        log.info("Removing course id={} from employee id={}", courseId, employeeId);

        Course course = courseRepo.findById(courseId).orElseThrow(() -> {
            log.error("Course with id={} not found", courseId);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        });

        if (course.getTeacher() == null || course.getTeacher().getId() != employeeId) {
            log.warn("Course id={} is not assigned to employee id={}", courseId, employeeId);
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Course is not assigned to this employee");
        }

        course.setTeacher(null);
        courseRepo.save(course);
        log.info("Course id={} removed from employee id={} successfully", courseId, employeeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getCourses(Integer employeeId) {
        log.info("Fetching courses for employee id={}", employeeId);
        // ensure employee exists (optional but helpful)
        if (!repo.existsById(employeeId)) {
            log.error("Employee with id={} not found when fetching courses", employeeId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        return courseRepo.findAllByTeacher_Id(employeeId);
    }
}