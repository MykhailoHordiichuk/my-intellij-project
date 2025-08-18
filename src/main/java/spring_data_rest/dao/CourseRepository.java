package spring_data_rest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import spring_data_rest.entity.Course;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findAllByTeacher_Id(Integer teacherId);
}