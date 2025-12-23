# Render Deployment Guide

## Automatic Deployment Steps:

### 1. Push Code to GitHub
```bash
cd "C:\Users\shivn\OneDrive\Desktop\Java project\tasktracker"
git init
git add .
git commit -m "Initial commit - Ready for Render deployment"
git branch -M main
git remote add origin https://github.com/Aditya40104/Task-Tracker.git
git push -u origin main
```

### 2. Sign Up on Render
- Go to https://render.com
- Sign up with your GitHub account

### 3. Create MySQL Database on Render
- Click "New +" → "PostgreSQL" (Render free tier doesn't support MySQL)
- **OR** use external MySQL service like:
  - PlanetScale (free MySQL)
  - Railway (free MySQL)
  - Aiven (free MySQL)

### 4. Create Web Service on Render
- Click "New +" → "Web Service"
- Connect your GitHub repository: `Aditya40104/Task-Tracker`
- Configure:
  - **Name:** tasktracker
  - **Environment:** Java
  - **Build Command:** `./mvnw clean install -DskipTests`
  - **Start Command:** `java -jar target/tasktracker-0.0.1-SNAPSHOT.jar`
  - **Instance Type:** Free

### 5. Add Environment Variables on Render Dashboard
Go to "Environment" tab and add:

```
DATABASE_URL=jdbc:mysql://YOUR_MYSQL_HOST:3306/tasktracker?useSSL=true
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password
PORT=10000
```

### 6. Deploy
- Click "Create Web Service"
- Render will automatically build and deploy your app
- You'll get a URL like: https://tasktracker.onrender.com

---

## Alternative: Use PostgreSQL Instead (Easier on Render)

If you want to use Render's free PostgreSQL instead of MySQL:

1. Change dependency in pom.xml:
```xml
<!-- Replace MySQL with PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. Update application.properties:
```properties
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

3. On Render, DATABASE_URL will be automatically set by Render's PostgreSQL

---

## Important Notes:
- First deployment takes 5-10 minutes
- Free tier apps sleep after 15 minutes of inactivity
- Wake up time: ~30 seconds on first request
- Perfect for portfolio/demo projects
