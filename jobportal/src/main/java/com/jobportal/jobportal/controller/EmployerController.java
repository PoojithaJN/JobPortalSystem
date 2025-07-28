package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.Application;
import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.model.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ApplicationRepository applicationRepo;

    // Dashboard
    @GetMapping("/dashboard")
    public String employerDashboard(Model model, Principal principal) {
        User employer = getLoggedInUser(principal.getName());
        model.addAttribute("employer", employer);
        return "employer-dashboard";
    }

    // View Posted Jobs
    @GetMapping("/jobs")
    public String viewPostedJobs(Model model, Principal principal) {
        User employer = getLoggedInUser(principal.getName());
        List<Job> jobs = jobRepo.findByPostedBy(employer);
        model.addAttribute("jobs", jobs);
        return "employer-jobs";
    }

    // Show Post Job Form
    @GetMapping("/post")
    public String showPostForm(Model model) {
        model.addAttribute("job", new Job());
        return "post-job";
    }

    // Alternate Add Job Form (optional)
    @GetMapping("/add")
    public String showAddJobForm(Model model) {
        model.addAttribute("job", new Job());
        return "employer-add-job";
    }

    // Save New Job
    @PostMapping("/post")
    public String postJob(@ModelAttribute Job job, Principal principal) {
        User employer = getLoggedInUser(principal.getName());
        job.setPostedBy(employer);
        jobRepo.save(job);
        return "redirect:/employer/jobs";
    }

    // Edit Existing Job
    @GetMapping("/edit/{id}")
    public String editJob(@PathVariable Long id, Model model, Principal principal) {
        User employer = getLoggedInUser(principal.getName());
        Job job = jobRepo.findById(id).orElse(null);
        if (job == null || !job.getPostedBy().getId().equals(employer.getId())) {
            return "redirect:/employer/jobs?error=unauthorized";
        }
        model.addAttribute("job", job);
        return "edit-job";
    }

    // Update Job
    @PostMapping("/update")
    public String updateJob(@ModelAttribute Job job, RedirectAttributes redirectAttributes) {
        jobRepo.save(job);
        redirectAttributes.addFlashAttribute("updated", true);
        return "redirect:/employer/jobs";
    }

    // Save Job (used by /add)
    @PostMapping("/save")
    public String saveJob(@ModelAttribute("job") Job job, Principal principal) {
        User employer = getLoggedInUser(principal.getName());
        job.setPostedBy(employer);
        jobRepo.save(job);
        return "redirect:/employer/jobs";
    }

    // Delete Job
    @GetMapping("/delete/{id}")
    public String deleteJob(@PathVariable Long id, Principal principal) {
        User employer = getLoggedInUser(principal.getName());
        Job job = jobRepo.findById(id).orElse(null);
        if (job != null && job.getPostedBy().getId().equals(employer.getId())) {
            jobRepo.delete(job);
        }
        return "redirect:/employer/jobs";
    }

    // View Applicants for All Employer's Jobs
    @GetMapping("/applicants")
    public String viewApplicants(Model model, Authentication authentication) {
        String username = authentication.getName();
        User employer = userRepo.findByUsername(username);
        List<Job> jobs = jobRepo.findByPostedBy(employer);

        List<Application> allApplications = new ArrayList<>();
        for (Job job : jobs) {
            if (job.getApplications() != null) {
                allApplications.addAll(job.getApplications());
            }
        }

        model.addAttribute("applications", allApplications);
        return "employer-applicants";
    }

    // Helper method to get user by username or email
    private User getLoggedInUser(String name) {
        User user = userRepo.findByUsername(name);
        return (user != null) ? user : userRepo.findByEmail(name);
    }
}
