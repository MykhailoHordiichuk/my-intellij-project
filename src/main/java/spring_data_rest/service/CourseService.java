package spring_data_rest.service;

import spring_data_rest.entity.Course;
import java.util.List;

public interface CourseService {
    Course create(Course input);
    Course get(Integer id);
    List<Course> getAll();
    Course update(Integer id, Course input);
    void delete(Integer id);
}