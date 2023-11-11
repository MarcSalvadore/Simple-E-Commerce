package com.apapedia.frontend_webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
