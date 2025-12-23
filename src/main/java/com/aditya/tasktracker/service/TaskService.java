package com.aditya.tasktracker.service;

import com.aditya.tasktracker.model.Task;
import com.aditya.tasktracker.model.TaskStatus;
import com.aditya.tasktracker.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TaskService - handles all business logic related to tasks
 * This is the "brain" that makes decisions about tasks
 */
@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    
    /**
     * Create a new task (only admin can do this)
     */
    public Task createTask(String title, String description, Long assignedToEmployeeId) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setAssignedToEmployeeId(assignedToEmployeeId);
        task.setStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }
    
    /**
     * Get all tasks assigned to a specific employee
     */
    public List<Task> getTasksForEmployee(Long employeeId) {
        return taskRepository.findByAssignedToEmployeeId(employeeId);
    }
    
    /**
     * Get all tasks (admin view)
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    /**
     * Update task status (employee marks task as DONE)
     */
    public Optional<Task> updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setStatus(newStatus);
            return Optional.of(taskRepository.save(task));
        }
        
        return Optional.empty();
    }
    
    /**
     * Find task by ID
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    
    /**
     * Delete task by ID
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
