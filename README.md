# 🧑‍💼 Job Portal System

A full-stack web application where employers can post jobs and applicants can search, apply, and track job applications.

---

## 🚀 Features

- 🔐 Role-based access: Employer & Applicant
- 📝 Employers: Post and manage job listings
- 👤 Applicants: Search jobs, apply, upload resume (PDF)
- 🔍 Keyword-based job search and filtering
- 🛡️ Secure login and registration (Spring Security)
- 📎 Resume upload & download
- 📱 Responsive UI using Bootstrap

---

## 🛠️ Tech Stack

- Java 17  
- Spring Boot 3  
- Spring Security  
- Spring Data JPA (Hibernate)  
- Thymeleaf (UI)  
- MySQL  
- Bootstrap 5  
- Maven

---

## ⚙️ Setup Instructions

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/jobportal.git
   cd jobportal
   
2. Create MySQL Database:
Login to your MySQL and create the database:
CREATE DATABASE job_portal;

3. Import the Database Dump
Import the provided SQL file (jobportal.sql):
Using MySQL CLI:
mysql -u root -p job_portal < jobportal.sql

Using MySQL Workbench:
Open MySQL Workbench
Open jobportal.sql
Select the job_portal schema
Execute All (⚡)

✅ This sets up the tables: user, job, application with sample data.

📄 Pages
/login – Login page
/register – Registration
/applicant/jobs – Browse Jobs
/applicant/my-applications – View Applied Jobs
/applicant/profile – Edit Profile
/employer/dashboard – Employer Dashboard
/employer/post-job – Post a Job


