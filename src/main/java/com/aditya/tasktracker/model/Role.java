package com.aditya.tasktracker.model;

/**
 * Role enum - defines the type of user
 * ADMIN = Manager (can create tasks, assign work)
 * EMPLOYEE = Worker (can see and update tasks)
 */
public enum Role {
    ADMIN,
    EMPLOYEE
}
