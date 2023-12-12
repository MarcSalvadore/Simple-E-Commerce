package com.apapedia.frontend_webapp.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.apapedia.frontend_webapp.dto.request.UpdateUserRequestDTO;
import com.apapedia.frontend_webapp.dto.request.WithdrawRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.dto.response.UpdateUserResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;
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
            return "redirect:/login-sso";
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
    public String formEditProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        UUID userID = userService.getUserIdFromToken(jwtToken);
        CreateUserResponseDTO user = userService.getUserDetails(userID, jwtToken);
        UpdateUserRequestDTO updateUserRequestDTO = new UpdateUserRequestDTO();

        updateUserRequestDTO.setId(userID);
        updateUserRequestDTO.setAddress(user.getAddress());
        updateUserRequestDTO.setName(user.getName());
        updateUserRequestDTO.setEmail(user.getEmail());
        updateUserRequestDTO.setUsername(user.getUsername());
        updateUserRequestDTO.setPassword(user.getPassword());
        
        model.addAttribute("userDTO", updateUserRequestDTO);
        return "profile/edit-profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(@Valid @ModelAttribute UpdateUserRequestDTO updateUserRequestDTO, HttpServletRequest request, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(); //Menginisiasi error message
            
            //Mengambil setiap error message yang ada
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); //Menampilkan error message dengan tampilan ke bawah
            }
            
            redirectAttributes.addFlashAttribute("userDTO", updateUserRequestDTO);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/profile/edit";
        }

        UpdateUserResponseDTO user = userService.editProfile(updateUserRequestDTO, jwtToken);

        if (user != null) {
            model.addAttribute("userDTO", user);
            model.addAttribute("success", "Profil sudah ter-update!");
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "Edit profile gagal");
            return "profile/edit-profile";
        }

    }
}
