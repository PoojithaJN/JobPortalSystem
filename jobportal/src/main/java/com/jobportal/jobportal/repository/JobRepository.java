package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // ✅ Fetch jobs posted by a specific employer (with applications, if needed)
    @Query("SELECT j FROM Job j LEFT JOIN FETCH j.applications WHERE j.postedBy = :employer")
    List<Job> findByPostedByWithApplications(@Param("employer") User employer);

    // ✅ Fetch jobs posted by employer (without joining applications)
    List<Job> findByPostedBy(User employer);
    
    List<Job> findByUser(User user);

    // ✅ Smart Search: title, company or location (case-insensitive)
    List<Job> findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String title, String company, String location
    );

    // ✅ Optional simple title search (case-insensitive)
    List<Job> findByTitleContainingIgnoreCase(String title);
}
