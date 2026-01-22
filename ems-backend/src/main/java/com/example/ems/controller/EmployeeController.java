package com.example.ems.controller;

import com.example.ems.entity.Employee;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    // ✅ Create Employee
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // ✅ Get All Employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    // ✅ Get Employee By Id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employee) {

        return employeeRepository.findById(id).map(existing -> {

            existing.setEmployeeCode(employee.getEmployeeCode());
            existing.setFullName(employee.getFullName());
            existing.setEmail(employee.getEmail());
            existing.setMobile(employee.getMobile());
            existing.setDepartment(employee.getDepartment());
            existing.setDateOfJoining(employee.getDateOfJoining());
            existing.setRole(employee.getRole());

            return ResponseEntity.ok(employeeRepository.save(existing));

        }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

        return employeeRepository.findById(id).map(emp -> {
            employeeRepository.delete(emp);
            return ResponseEntity.ok("Employee deleted successfully");
        }).orElse(ResponseEntity.notFound().build());
    }
}
