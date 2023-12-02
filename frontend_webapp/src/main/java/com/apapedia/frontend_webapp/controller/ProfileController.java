package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class ProfileController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserService userService;

    @GetMapping("profile")
    public String profilePage(@CookieValue(name = "token", required = false) String token, HttpServletRequest request, Model model) {
        System.out.println("INI TOKEN");
        System.out.println(token);

        if (!jwtUtils.validateToken(token)) {
            return "redirect:/login";
        }

        UUID userId = userService.getUserIdFromToken(token);
        CreateUserResponseDTO seller = userService.getUserDetails(userId, token);
        System.out.println("INI DATA");
        System.out.println(seller.getAddress());
        System.out.println(seller.getUsername());
        model.addAttribute("userDTO", seller);
        
        // if (token != null) {
        //     CreateUserResponseDTO userInfo = userService.getuse
        //     model.addAttribute("userInfo", userInfo);
        //     return "profile/profile";
        // } else {
        //     // Token tidak ditemukan, mungkin pengguna belum login
        //     return "redirect:/login"; // Redirect ke halaman login atau halaman lain sesuai kebutuhan
        // }


        return "profile/profile";
    }

    @GetMapping("withdraw")
    public String formWithdraw(Model model) {
        return "profile/withdraw";
    }

    @GetMapping("profile/edit")
    public String editProfile(Model model) {
        return "profile/edit-profile";
    }
}
