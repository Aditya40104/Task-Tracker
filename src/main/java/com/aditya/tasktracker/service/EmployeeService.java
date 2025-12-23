package com.aditya.tasktracker.service;

import com.aditya.tasktracker.model.Employee;
import com.aditya.tasktracker.model.Role;
import com.aditya.tasktracker.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeService - handles all business logic related to employees
 * This is the "brain" that makes decisions
 */
@Service
public class EmployeeService {
    
    private final EmployeeRepository employeeRepository;
    
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    
    /**
     * Login logic - check if email and password are correct
     */
    public Optional<Employee> login(String email, String password) {
        Optional<Employee> employee = employeeRepository.findByEmail(email);
        
        // Check if employee exists AND password matches
        if (employee.isPresent() && employee.get().getPassword().equals(password)) {
            return employee;
        }
        
        return Optional.empty(); // Login failed
    }
    
    /**
     * Create a new employee (only admin can do this)
     */
    public Employee createEmployee(String name, String email, String password, Role role) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setPassword(password);
        employee.setRole(role);
        return employeeRepository.save(employee);
    }
    
    /**
     * Get all employees
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    /**
     * Find employee by ID
     */
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }
    
    /**
     * Check if employee is admin
     */
    public boolean isAdmin(Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        return employee.isPresent() && employee.get().isAdmin();
    }
}
