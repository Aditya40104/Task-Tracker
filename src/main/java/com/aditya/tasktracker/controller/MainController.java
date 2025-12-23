package com.aditya.tasktracker.controller;

import com.aditya.tasktracker.model.Employee;
import com.aditya.tasktracker.model.Role;
import com.aditya.tasktracker.model.Task;
import com.aditya.tasktracker.model.TaskStatus;
import com.aditya.tasktracker.service.EmployeeService;
import com.aditya.tasktracker.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * MainController - handles all web requests
 * This connects the browser to the backend
 * 
 * Flow: Browser → Controller → Service → Repository → Database
 */
@Controller
public class MainController {
    
    private final EmployeeService employeeService;
    private final TaskService taskService;
    
    public MainController(EmployeeService employeeService, TaskService taskService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
    }
    
    /**
     * Home/Landing page
     * URL: http://localhost:8080/
     */
    @GetMapping("/")
    public String home() {
        return "index"; // shows index.html
    }
    
    /**
     * Show login page
     * URL: http://localhost:8080/login
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // shows login.html
    }
    
    /**
     * Handle login form submission
     * When user clicks "Login" button
     */
    @PostMapping("/login")
    public String handleLogin(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        
        Optional<Employee> employeeOpt = employeeService.login(email, password);
        
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            
            // Save employee in session (so we remember who logged in)
            session.setAttribute("employeeId", employee.getId());
            session.setAttribute("employeeName", employee.getName());
            session.setAttribute("employeeRole", employee.getRole());
            
            // Redirect to appropriate dashboard
            if (employee.isAdmin()) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/employee/dashboard";
            }
        } else {
            // Login failed
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }
    
    /**
     * Admin Dashboard
     * Shows all tasks and all employees
     */
    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        // Check if user is logged in and is admin
        if (!isLoggedInAsAdmin(session)) {
            return "redirect:/login";
        }
        
        String employeeName = (String) session.getAttribute("employeeName");
        List<Task> allTasks = taskService.getAllTasks();
        List<Employee> allEmployees = employeeService.getAllEmployees();
        
        model.addAttribute("employeeName", employeeName);
        model.addAttribute("tasks", allTasks);
        model.addAttribute("employees", allEmployees);
        
        return "admin-dashboard";
    }
    
    /**
     * Employee Dashboard
     * Shows tasks assigned to this employee only
     */
    @GetMapping("/employee/dashboard")
    public String employeeDashboard(HttpSession session, Model model) {
        // Check if user is logged in
        Long employeeId = (Long) session.getAttribute("employeeId");
        if (employeeId == null) {
            return "redirect:/login";
        }
        
        String employeeName = (String) session.getAttribute("employeeName");
        List<Task> myTasks = taskService.getTasksForEmployee(employeeId);
        
        model.addAttribute("employeeName", employeeName);
        model.addAttribute("tasks", myTasks);
        
        return "employee-dashboard";
    }
    
    /**
     * Create new employee (Admin only)
     */
    @PostMapping("/admin/employee/create")
    public String createEmployee(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role,
            HttpSession session) {
        
        if (!isLoggedInAsAdmin(session)) {
            return "redirect:/login";
        }
        
        Role employeeRole = "ADMIN".equals(role) ? Role.ADMIN : Role.EMPLOYEE;
        employeeService.createEmployee(name, email, password, employeeRole);
        
        return "redirect:/admin/dashboard";
    }
    
    /**
     * Create new task (Admin only)
     */
    @PostMapping("/admin/task/create")
    public String createTask(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam Long assignedTo,
            HttpSession session) {
        
        if (!isLoggedInAsAdmin(session)) {
            return "redirect:/login";
        }
        
        taskService.createTask(title, description, assignedTo);
        
        return "redirect:/admin/dashboard";
    }
    
    /**
     * Update task status (Employee marks task as DONE)
     */
    @PostMapping("/employee/task/update")
    public String updateTaskStatus(
            @RequestParam Long taskId,
            @RequestParam String status,
            HttpSession session) {
        
        Long employeeId = (Long) session.getAttribute("employeeId");
        if (employeeId == null) {
            return "redirect:/login";
        }
        
        TaskStatus newStatus = "DONE".equals(status) ? TaskStatus.DONE : TaskStatus.TODO;
        taskService.updateTaskStatus(taskId, newStatus);
        
        return "redirect:/employee/dashboard";
    }
    
    /**
     * Logout
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear all session data
        return "redirect:/login";
    }
    
    /**
     * Helper method to check if user is admin
     */
    private boolean isLoggedInAsAdmin(HttpSession session) {
        Role role = (Role) session.getAttribute("employeeRole");
        return role != null && role == Role.ADMIN;
    }
}
