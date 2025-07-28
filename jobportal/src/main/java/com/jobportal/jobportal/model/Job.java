package com.jobportal.jobportal.model;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Job {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private String title;
    
    private String description;
    private String location;
    private String company;

    @ManyToOne
    private User postedBy;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Application> applications;
    
    @ManyToOne
    @JoinColumn(name = "user_id") // or "employer_id" if you prefer
    private User user; // this is the employer who posted the job


    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public User getPostedBy() { return postedBy; }
    public void setPostedBy(User postedBy) { this.postedBy = postedBy; }
    
    private String status = "OPEN"; // Default

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
	
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Application> getApplications() { return applications; }
    public void setApplications(List<Application> applications) { this.applications = applications; }

   

}

