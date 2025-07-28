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
   
---

### 2. Create the Database

```sql
CREATE DATABASE job_portal;
```

---

### 3. Import the Database Dump using MySQL Workbench

To import the sample data using **MySQL Workbench**:

1. **Open MySQL Workbench**
2. **Connect to your MySQL server**
   - Select the MySQL connection (e.g., `Localhost`) and connect.
3. **Open the SQL file**:
   - Go to `File` → `Open SQL Script`
   - Select the provided file `jobportal.sql`
4. **Choose the database**:
   - At the top, select the `job_portal` schema.
   - If not visible, click the `Refresh` icon in the "Schemas" panel.
5. **Run the script**:
   - Click the ⚡ lightning bolt icon (or press `Ctrl + Shift + Enter`)
   - This will execute all SQL statements and create the tables with sample data

---

### ✅ Tables Created

- `user`
- `job`
- `application`

---

## 🌐 Pages

- `/login` – Login page  
- `/register` – Registration page  
- `/applicant/jobs` – Browse available jobs  
- `/applicant/my-applications` – View submitted job applications  
- `/applicant/profile` – Edit applicant profile and upload resume  
- `/employer/dashboard` – Employer dashboard to manage job postings  
- `/employer/post-job` – Form to post a new job  
