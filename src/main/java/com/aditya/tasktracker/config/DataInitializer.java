package com.aditya.tasktracker.config;

import com.aditya.tasktracker.model.Role;
import com.aditya.tasktracker.service.EmployeeService;
import com.aditya.tasktracker.service.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DataInitializer - adds sample data when application starts
 * This creates demo users and tasks so you can test immediately
 */
@Configuration
public class DataInitializer {
    
    @Bean
    CommandLineRunner initData(EmployeeService employeeService, TaskService taskService) {
        return args -> {
            // Check if data already exists
            if (!employeeService.getAllEmployees().isEmpty()) {
                System.out.println("✅ Sample data already exists, skipping initialization.");
                return;
            }
            
            System.out.println("🌱 Initializing sample data...");
            
            // Create Admin user
            var admin = employeeService.createEmployee(
                "Admin User", 
                "admin@company.com", 
                "admin123", 
                Role.ADMIN
            );
            System.out.println("✅ Created Admin: " + admin.getEmail());
            
            // Create Employee users
            var john = employeeService.createEmployee(
                "John Doe", 
                "john@company.com", 
                "john123", 
                Role.EMPLOYEE
            );
            System.out.println("✅ Created Employee: " + john.getEmail());
            
            var sarah = employeeService.createEmployee(
                "Sarah Smith", 
                "sarah@company.com", 
                "sarah123", 
                Role.EMPLOYEE
            );
            System.out.println("✅ Created Employee: " + sarah.getEmail());
            
            var mike = employeeService.createEmployee(
                "Mike Johnson", 
                "mike@company.com", 
                "mike123", 
                Role.EMPLOYEE
            );
            System.out.println("✅ Created Employee: " + mike.getEmail());
            
            // Create sample tasks for John
            taskService.createTask(
                "Update customer database",
                "Update all customer records with new fields and validate data integrity",
                john.getId()
            );
            
            taskService.createTask(
                "Fix login page bug",
                "Users are unable to login with email. Check authentication flow.",
                john.getId()
            );
            
            taskService.createTask(
                "Write API documentation",
                "Document all REST API endpoints with request/response examples",
                john.getId()
            );
            
            // Create sample tasks for Sarah
            taskService.createTask(
                "Design new homepage",
                "Create mockups for the new company homepage redesign",
                sarah.getId()
            );
            
            taskService.createTask(
                "Prepare monthly report",
                "Compile sales data and create presentation for management",
                sarah.getId()
            );
            
            // Create sample tasks for Mike
            taskService.createTask(
                "Setup CI/CD pipeline",
                "Configure Jenkins for automated testing and deployment",
                mike.getId()
            );
            
            taskService.createTask(
                "Review code submissions",
                "Review and approve pending pull requests in GitHub",
                mike.getId()
            );
            
            System.out.println("✅ Created 7 sample tasks");
            System.out.println("🎉 Sample data initialization complete!");
            System.out.println("");
            System.out.println("===========================================");
            System.out.println("🔐 LOGIN CREDENTIALS:");
            System.out.println("===========================================");
            System.out.println("👨‍💼 ADMIN:");
            System.out.println("   Email: admin@company.com");
            System.out.println("   Password: admin123");
            System.out.println("");
            System.out.println("👨‍💻 EMPLOYEES:");
            System.out.println("   Email: john@company.com    | Password: john123");
            System.out.println("   Email: sarah@company.com   | Password: sarah123");
            System.out.println("   Email: mike@company.com    | Password: mike123");
            System.out.println("===========================================");
            System.out.println("🌐 Open: http://localhost:8080");
            System.out.println("===========================================");
        };
    }
}
