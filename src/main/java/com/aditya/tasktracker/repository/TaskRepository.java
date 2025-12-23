package com.aditya.tasktracker.repository;

import com.aditya.tasktracker.model.Task;
import com.aditya.tasktracker.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TaskRepository - manages task data in MySQL database
 * Spring Data JPA provides automatic implementation
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    /**
     * Find all tasks assigned to a specific employee
     * Spring Data JPA automatically implements this based on method name
     */
    List<Task> findByAssignedToEmployeeId(Long employeeId);
    
    /**
     * Find all tasks with a specific status
     */
    List<Task> findByStatus(TaskStatus status);
}
