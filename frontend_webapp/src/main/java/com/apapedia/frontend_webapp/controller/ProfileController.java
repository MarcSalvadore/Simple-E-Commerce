package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.apapedia.frontend_webapp.dto.request.WithdrawRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ChangePasswordResponseDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;

import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


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
            return "redirect:/logout-sso";
        }

        UUID userId = userService.getUserIdFromToken(jwtToken);
        CreateUserResponseDTO seller = userService.getUserDetails(userId, jwtToken);

        model.addAttribute("userDTO", seller);
        return "profile/profile";
    }

    @GetMapping("/withdraw")
    public String formWithdraw(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");

        UUID idSeller = userService.getUserIdFromToken(jwtToken);
        CreateUserResponseDTO seller = userService.getUserDetails(idSeller, jwtToken);
        WithdrawRequestDTO withdraw = new WithdrawRequestDTO();

        withdraw.setIdSeller(idSeller);
        withdraw.setBalance(seller.getBalance());

        model.addAttribute("withdraw", withdraw);
        return "profile/withdraw";
    }

    @PostMapping(value="/withdraw")
    public String withdraw(WithdrawRequestDTO withdrawRequestDTO, HttpServletRequest request, RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        
        if (withdrawRequestDTO.getAmount() == null || withdrawRequestDTO.getAmount() == (long) 0) {
            model.addAttribute("error", "Harap isi jumlah lebih dari 0");
            model.addAttribute("withdraw", withdrawRequestDTO);
            return "profile/withdraw";
        }
        
        if (withdrawRequestDTO.getAmount() > withdrawRequestDTO.getBalance()) {
            model.addAttribute("error", "Balance tidak mencukupi");
            model.addAttribute("withdraw", withdrawRequestDTO);
            return "profile/withdraw";
        } else {
            String success = userService.withdraw(withdrawRequestDTO, jwtToken);

            redirectAttributes.addFlashAttribute("success", success);
            return "redirect:/profile";
        }
    }

    @GetMapping("/profile/edit")
    public String editProfilePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");

        if (!jwtUtils.validateToken(jwtToken)) {
            return "redirect:/login-sso";
        }

        UUID userId = userService.getUserIdFromToken(jwtToken);
        CreateUserResponseDTO seller = userService.getUserDetails(userId, jwtToken);
        model.addAttribute("seller", seller);

        return "profile/edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(HttpServletRequest request, Model model, @ModelAttribute CreateUserResponseDTO createUserResponseDTO) {
        
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");

        if (!jwtUtils.validateToken(jwtToken)) {
            return "redirect:/login-sso";
        }

        UUID userId = userService.getUserIdFromToken(jwtToken);
        CreateUserResponseDTO seller = userService.editUser(userId, jwtToken, createUserResponseDTO);

        model.addAttribute("userDTO", seller);
        return "profile/profile";
    }

    @GetMapping("/profile/setting")
    public String settingPage(Model model) {
        return "profile/setting";
    }

    @GetMapping("/profile/edit/password")
    public String changePasswordPage(Model model) {
        model.addAttribute("changePasswordDTO", new ChangePasswordResponseDTO());
        return "profile/change-password";
    }

    @PostMapping("/profile/edit/password")
    public String changePassword(@Valid @ModelAttribute ChangePasswordResponseDTO changePasswordDTO, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");

        if (!jwtUtils.validateToken(jwtToken)) {
            return "redirect:/login-sso";
        }

        try {
            UUID userId = userService.getUserIdFromToken(jwtToken);
            userService.changePassword(userId, jwtToken, changePasswordDTO);
            redirectAttributes.addFlashAttribute("success", "Password changed successfully");
            return "redirect:/profile/setting";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Check your current password and make sure new password must be different from the current password ");
            return "redirect:/profile/edit/password";
        }
    }
}
