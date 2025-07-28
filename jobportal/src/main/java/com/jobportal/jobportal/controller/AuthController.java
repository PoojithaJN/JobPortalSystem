package com.jobportal.jobportal.controller;

import com.jobportal.jobportal.model.Job;
import com.jobportal.jobportal.model.User;
import com.jobportal.jobportal.repository.JobRepository;
import com.jobportal.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JobRepository jobRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // === Registration ===
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String fullName,
                                  @RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String role,
                                  Model model) {

        if (userRepo.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "register";
        }

        User user = new User();
        user.setFullName(fullName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.toUpperCase());

        userRepo.save(user);
        return "redirect:/register?success";
    }

    // === Login ===
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // === Browse Jobs ===
    @GetMapping("/jobs")
    public String browseJobs(@RequestParam(required = false) String keyword, Model model) {
        List<Job> jobs;

        if (keyword != null && !keyword.trim().isEmpty()) {
            jobs = jobRepo.findByTitleContainingIgnoreCaseOrCompanyContainingIgnoreCaseOrLocationContainingIgnoreCase(
                    keyword, keyword, keyword);
        } else {
            jobs = jobRepo.findAll();
        }

        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        return "browse-jobs";
    }

    // === Landing page ===
    @GetMapping("/")
    public String landing() {
        return "landing";
    }

    // === Redirect after successful login ===
    @GetMapping("/default")
    public String redirectAfterLogin(Principal principal) {
        if (principal == null) return "redirect:/login";

        User user = getLoggedInUser(principal.getName());
        if (user == null) return "redirect:/login?error=true";

        switch (user.getRole().toUpperCase()) {
            case "EMPLOYER":
                return "redirect:/employer/dashboard";
            case "APPLICANT":
                return "redirect:/applicant/dashboard";
            case "ADMIN":
                return "redirect:/admin/dashboard";
            default:
                return "redirect:/login?error=role";
        }
    }

    private User getLoggedInUser(String name) {
        User user = userRepo.findByUsername(name);
        return (user != null) ? user : userRepo.findByEmail(name);
    }
}
