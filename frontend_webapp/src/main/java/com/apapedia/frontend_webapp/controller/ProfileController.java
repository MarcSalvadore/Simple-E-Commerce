package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String profilePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");

        if (!jwtUtils.validateToken(jwtToken)) {
            return "redirect:/login-sso";
        }

        UUID userId = userService.getUserIdFromToken(jwtToken);
        CreateUserResponseDTO seller = userService.getUserDetails(userId, jwtToken);
        model.addAttribute("userDTO", seller);
        
        return "profile/profile";
    }

    @GetMapping("/withdraw")
    public String formWithdraw(Model model) {
        return "profile/withdraw";
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model) {
        return "profile/edit-profile";
    }
}
