# MySQL Database Setup Instructions

## Before running the application, please create the MySQL database:

### Option 1: Using MySQL Workbench
1. Open MySQL Workbench
2. Connect to your MySQL server (localhost)
3. Execute this SQL command:
```sql
CREATE DATABASE IF NOT EXISTS tasktracker;
```

### Option 2: Using MySQL Command Line
1. Open Command Prompt or Terminal
2. Login to MySQL:
```bash
mysql -u root -p
```
3. Enter your password: Adit@2004
4. Create database:
```sql
CREATE DATABASE tasktracker;
```
5. Exit MySQL:
```sql
exit;
```

### Option 3: Using phpMyAdmin
1. Open phpMyAdmin in your browser
2. Click "New" in the left sidebar
3. Enter database name: tasktracker
4. Click "Create"

## After creating the database:
- Spring Boot will automatically create the tables (employees, tasks) when you start the application
- Sample data will be inserted automatically on first run

## Database Configuration (already configured in application.properties):
- URL: jdbc:mysql://localhost:3306/tasktracker
- Username: root
- Password: Adit@2004
