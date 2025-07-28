package com.jobportal.jobportal.repository;

import com.jobportal.jobportal.model.Application;
import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicant(User user);
    List<Application> findByJob(Job job);
    int countByJob(Job job); 

}
