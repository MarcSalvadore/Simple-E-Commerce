package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class UserController {
    private final WebClient webClient;

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    public UserController(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.SERVER_USER_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }

    // Register seller
    @GetMapping("/register")
    public String formRegister(Model model){
        var user = new CreateUserRequestDTO();

        model.addAttribute("userDTO", user);
        return "user/register";
    }

    @PostMapping(value = "/register")
    private String registerSeller(@Valid @ModelAttribute CreateUserRequestDTO userRequestDTO, HttpSession session, RedirectAttributes redirectAttributes){
        try {
            var response = this.webClient
                .post()
                .uri("/api/seller/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequestDTO)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/login-sso";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
            return "redirect:/register";
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
