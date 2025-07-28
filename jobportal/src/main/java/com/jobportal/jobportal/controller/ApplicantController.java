package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.Application;
import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.model.User;
import com.jobportal.jobportal.repository.ApplicationRepository;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import java.io.IOException;
import java.nio.file.*;
import java.security.Principal;
import java.util.List;
import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Browse Jobs
    @GetMapping("/jobs")
    public String browseJobs(@RequestParam(value = "keyword", required = false) String keyword,
                             Model model,
                             Principal principal,
                             HttpServletRequest request) {

        List<Job> jobs = (keyword != null && !keyword.isEmpty()) ?
                jobRepository.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
                        keyword, keyword, keyword)
                : jobRepository.findAll();

        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);

        User user = getLoggedInUser(principal.getName());
        model.addAttribute("username", user.getFullName());

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("_csrf", csrfToken);

        return "browse-jobs";
    }

    // ✅ Show Apply Page
    @GetMapping("/apply/{jobId}")
    public String showApplyPage(@PathVariable Long jobId,
                                Model model,
                                Principal principal,
                                HttpServletRequest request) {

        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            return "redirect:/applicant/jobs?error=JobNotFound";
        }

        User user = getLoggedInUser(principal.getName());
        model.addAttribute("job", job);
        model.addAttribute("username", user.getFullName());

        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        model.addAttribute("csrf", csrfToken);

        return "apply-job";
    }

    // ✅ Submit Application
    @PostMapping("/apply/{jobId}")
    public String applyToJob(@PathVariable Long jobId,
                             @RequestParam("resumeFile") MultipartFile file,
                             Principal principal) throws IOException {

        if (file.isEmpty()) {
            return "redirect:/applicant/jobs?error=NoFile";
        }

        User applicant = getLoggedInUser(principal.getName());
        Job job = jobRepository.findById(jobId).orElse(null);

        if (job == null) {
            return "redirect:/applicant/jobs?error=JobNotFound";
        }

        String originalName = file.getOriginalFilename();
        if (originalName == null || !originalName.toLowerCase().endsWith(".pdf")) {
            return "redirect:/applicant/jobs?error=InvalidFile";
        }

        String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(originalName);
        Path uploadPath = Paths.get("src/main/resources/static/uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        Application application = new Application();
        application.setApplicant(applicant);
        application.setJob(job);
        application.setResumeFilename(fileName);
        application.setCreatedAt(LocalDateTime.now());

        applicationRepository.save(application);

        return "redirect:/applicant/my-applications?success";
    }

    // ✅ View My Applications
    @GetMapping("/my-applications")
    public String myApplications(Model model, Principal principal) {
        User applicant = getLoggedInUser(principal.getName());
        model.addAttribute("applications", applicationRepository.findByApplicant(applicant));
        return "my-applications";
    }

    // ✅ Dashboard
    @GetMapping("/applicant-dashboard")
    public String dashboard(Model model, Principal principal) {
        User applicant = getLoggedInUser(principal.getName());
        model.addAttribute("username", applicant.getFullName());
        model.addAttribute("applicant", applicant);
        return "applicant-dashboard";
    }

    // ✅ View Profile
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        User user = getLoggedInUser(principal.getName());

        if (user == null) {
            return "redirect:/login"; // Or show an error page
        }

        model.addAttribute("user", user);
        return "profile";
    }

    // ✅ Update Profile
    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser,
                                @RequestParam("resumeFile") MultipartFile resumeFile,
                                Principal principal,
                                RedirectAttributes redirectAttributes) throws IOException {

        User user = getLoggedInUser(principal.getName()); // ✅ FIXED: Avoid null

        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/login";
        }

        user.setFullName(updatedUser.getFullName());
        user.setPhone(updatedUser.getPhone());

        if (!resumeFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + resumeFile.getOriginalFilename();
            Path uploadPath = Paths.get("src/main/resources/static/uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Files.copy(resumeFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            user.setResumePath(fileName);
        }

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");

        return "redirect:/applicant/applicant-dashboard";
    }


    // ✅ Utility method to fetch user
    private User getLoggedInUser(String name) {
        User user = userRepository.findByUsername(name);
        return (user != null) ? user : userRepository.findByEmail(name);
    }
}
