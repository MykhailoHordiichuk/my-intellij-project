package spring_data_rest.controller;

import spring_data_rest.dao.EmployeeRepository;
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
@RequestMapping("/api/employees")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // Получить всех сотрудников
    @GetMapping
    public List<Employee> getAllEmployees() {
        logger.info("GET /api/employees - getAllEmployees called");
        List<Employee> employees = employeeRepository.findAll();
        logger.debug("Found {} employees", employees.size());
        return employees;
    }

    // Получить сотрудника по ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        logger.info("GET /api/employees/{} - getEmployeeById called", id);
        Optional<Employee> empOpt = employeeRepository.findById(id);
        if (empOpt.isPresent()) {
            logger.debug("Employee found: {}", empOpt.get());
            return ResponseEntity.ok(empOpt.get());
        } else {
            logger.warn("Employee with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Добавить нового сотрудника
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        logger.info("POST /api/employees - addEmployee called");
        logger.debug("Employee to save: {}", employee);
        Employee saved = employeeRepository.save(employee);
        logger.info("Employee saved with ID {}", saved.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Обновить данные сотрудника по ID
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        logger.info("PUT /api/employees/{} - updateEmployee called", id);
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            logger.warn("Employee with ID {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID = " + id + " not found");
        }

        Employee employee = optionalEmployee.get();
        logger.debug("Existing employee before update: {}", employee);

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setLanguage(updatedEmployee.getLanguage());
        employee.setLevel(updatedEmployee.getLevel());
        employee.setExperienceYears(updatedEmployee.getExperienceYears());
        employee.setHourlyRate(updatedEmployee.getHourlyRate());

        Employee saved = employeeRepository.save(employee);
        logger.info("Employee with ID {} updated", saved.getId());
        return ResponseEntity.ok(saved);
    }

    // Удалить сотрудника по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        logger.info("DELETE /api/employees/{} - deleteEmployee called", id);
        if (!employeeRepository.existsById(id)) {
            logger.warn("Employee with ID {} not found for deletion", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID = " + id + " not found");
        }

        employeeRepository.deleteById(id);
        logger.info("Employee with ID {} deleted", id);
        return ResponseEntity.ok("Employee with ID = " + id + " was deleted");
    }
}