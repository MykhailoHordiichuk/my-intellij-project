package spring_data_rest.service;

import spring_data_rest.entity.Employee;
import spring_data_rest.entity.Course;
import java.util.List;

public interface EmployeeService {
    Employee create(Employee input);
    Employee get(Integer id);
    List<Employee> getAll();
    Employee update(Integer id, Employee input);
    void delete(Integer id);

    void assignCourse(Integer employeeId, Integer courseId);
    void removeCourse(Integer employeeId, Integer courseId);
    List<Course> getCourses(Integer employeeId);
}