package com.aditya.tasktracker.repository;

import com.aditya.tasktracker.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * EmployeeRepository - manages employee data in MySQL database
 * Spring Data JPA provides automatic implementation
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    /**
     * Find employee by email (used for login)
     * Spring Data JPA automatically implements this based on method name
     */
    Optional<Employee> findByEmail(String email);
}
