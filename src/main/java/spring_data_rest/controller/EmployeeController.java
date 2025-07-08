package spring_data_rest.controller;

import spring_data_rest.dao.EmployeeRepository;
import spring_data_rest.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Получить всех сотрудников
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Получить сотрудника по ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Добавить нового сотрудника
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // Обновить данные сотрудника по ID
    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with ID = " + id + " not found");
        }

        Employee employee = optionalEmployee.get();
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setLanguage(updatedEmployee.getLanguage());
        employee.setLevel(updatedEmployee.getLevel());
        employee.setExperienceYears(updatedEmployee.getExperienceYears());
        employee.setHourlyRate(updatedEmployee.getHourlyRate());

        Employee saved = employeeRepository.save(employee);
        return ResponseEntity.ok(saved);
    }

    // Удалить сотрудника по ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int id) {
        if (!employeeRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID = " + id + " not found");
        }

        employeeRepository.deleteById(id);
        return ResponseEntity.ok("Employee with ID = " + id + " was deleted");
    }
}