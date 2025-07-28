package com.jobportal.jobportal.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resumeUrl;
    
    @Column(nullable = false)
    private String resumeFilename;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User applicant;

    // ✅ Link to Job
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    // Getters and setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getResumeUrl() { return resumeUrl; }

    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }
    
    public String getResumeFilename() { return resumeFilename; }

    public void setResumeFilename(String resumeFilename) { 
    	this.resumeFilename = resumeFilename; }
    
    public User getApplicant() { return applicant; }

    public void setApplicant(User applicant) { this.applicant = applicant; }

    public Job getJob() { return job; }

    public void setJob(Job job) { this.job = job; }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
