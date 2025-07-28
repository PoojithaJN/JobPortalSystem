package com.jobportal.jobportal.config;

import com.jobportal.jobportal.model.User;
import com.jobportal.jobportal.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        String username = authentication.getName();

        // Fetch user using username or email
        User user = userRepository.findByUsername(username);
        if (user == null) {
            user = userRepository.findByEmail(username);
        }

        if (user != null) {
            // Optional: store in session
            request.getSession().setAttribute("currentUser", user);

            // Redirect based on role
            if ("EMPLOYER".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/employer/dashboard");
            } else if ("APPLICANT".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/applicant/jobs");
            } else {
                response.sendRedirect(request.getContextPath() + "/login?roleerror");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login?nouser");
        }
    }
}
