package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    // Register seller
    @GetMapping("/register")
    public String formRegister(Model model){
        var user = new CreateUserRequestDTO();

        model.addAttribute("userDTO", user);
        return "user/register";
    }

    @PostMapping("/register")
    private RedirectView registerSeller(@Valid @ModelAttribute CreateUserRequestDTO userRequestDTO, RedirectAttributes redirectAttributes){
        try {
            String uri = "http://localhost:8081/api/seller/create";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(uri, userRequestDTO, String.class);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return new RedirectView("/login-sso");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return new RedirectView("/register");
        }
    }

    @PostMapping("/user/delete")
    public RedirectView deleteUser(HttpServletRequest request, HttpServletResponse response, Model model, HttpSession session) {
        String token = (String) session.getAttribute("token");
        UUID userId = userService.getUserIdFromToken(token);
        try {
            userService.deleteUser(userId, token);
            model.addAttribute("message", "User successfully deleted");
            return new RedirectView("/logout-sso");
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting user: " + e.getMessage());
            return new RedirectView("/profile");
        }
    }

    
}
