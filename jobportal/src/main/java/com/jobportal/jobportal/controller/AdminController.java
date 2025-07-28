package com.jobportal.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.model.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private ApplicationRepository appRepo;

    // ✅ Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        List<User> users = userRepo.findAll();
        List<Job> jobs = jobRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("jobs", jobs);
        return "admin-dashboard";
    }

    // ✅ Delete a user
    @PostMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // ✅ Delete a job
    @PostMapping("/delete/job/{id}")
    public String deleteJob(@PathVariable Long id) {
        jobRepo.deleteById(id);
        return "redirect:/admin/dashboard";
    }

    // ✅ Show job application chart data
    @GetMapping("/charts")
    public String showCharts(Model model) {
        List<Job> jobs = jobRepo.findAll();
        List<String> jobTitles = new ArrayList<>();
        List<Integer> applicationCounts = new ArrayList<>();

        for (Job job : jobs) {
            jobTitles.add(job.getTitle());
            applicationCounts.add(appRepo.findByJob(job).size());
        }

        model.addAttribute("jobTitles", jobTitles);
        model.addAttribute("applicationCounts", applicationCounts);

        return "admin-charts";
    }

    // ✅ Export job data as CSV
    @GetMapping("/export-jobs")
    public void exportJobs(HttpServletResponse response) throws IOException {
        List<Job> jobs = jobRepo.findAll();
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=jobs.csv");

        PrintWriter writer = response.getWriter();
        writer.println("ID,Title,Company,Location,PostedBy");

        for (Job job : jobs) {
            String postedBy = (job.getPostedBy() != null)
                    ? job.getPostedBy().getUsername()
                    : "Unknown";

            writer.printf("%d,\"%s\",\"%s\",\"%s\",\"%s\"%n",
                    job.getId(),
                    job.getTitle(),
                    job.getCompany(),
                    job.getLocation(),
                    postedBy);
        }

        writer.flush();
        writer.close();
    }
}
