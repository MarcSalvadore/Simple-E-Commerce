package com.apapedia.frontend_webapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;

import jakarta.validation.Valid;

@Controller
public class ProfileController {
    @GetMapping("profile")
    public String profilePage(Model model) {
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
