package spring_data_rest.service.impl;

import spring_data_rest.dao.CourseRepository;
import spring_data_rest.entity.Course;
import spring_data_rest.service.CourseService;

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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repo;

    public CourseServiceImpl(CourseRepository repo) {
        this.repo = repo;
    }

    @Override
    public Course create(Course input) {
        log.info("Creating course with language={}, level={}", input.getLanguage(), input.getLevel());
        Course saved = repo.save(input);
        log.info("Course created successfully with id={}", saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Course get(Integer id) {
        log.info("Fetching course id={}", id);
        return repo.findById(id).orElseThrow(() -> {
            log.error("Course id={} not found", id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> getAll() {
        log.info("Fetching all courses");
        return repo.findAll();
    }

    @Override
    public Course update(Integer id, Course input) {
        log.info("Updating course id={}", id);
        Course existing = get(id);
        BeanUtils.copyProperties(input, existing, "id");
        Course saved = repo.save(existing);
        log.info("Course id={} updated successfully", id);
        return saved;
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting course id={}", id);
        if (!repo.existsById(id)) {
            log.error("Attempt to delete non-existing course id={}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        repo.deleteById(id);
        log.info("Course id={} deleted successfully", id);
    }
}