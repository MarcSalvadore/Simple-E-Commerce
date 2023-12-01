package com.apapedia.frontend_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.apapedia.frontend_webapp.dto.request.LoginJwtRequestDTO;
import com.apapedia.frontend_webapp.dto.response.CreateUserResponseDTO;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("register")
    public String formRegister(Model model){
        var user = new CreateUserRequestDTO();

        model.addAttribute("userDTO", user);
        return "user/register";
    }
    @PostMapping("register")
    public String registerSeller(@ModelAttribute CreateUserRequestDTO userDTO, Model model, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        CreateUserResponseDTO userResultDTO = userService.sendSeller(userDTO, jwtToken);
        // if (userResultDTO.getId() == null) {
        //     return 
        // }
        model.addAttribute("user", userResultDTO);
        return "success-add-user";
    }
    // @PostMapping("register")
    // private RedirectView registerSeller(@Valid @ModelAttribute CreateUserRequestDTO userRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
    //     if (bindingResult.hasErrors()) {
    //         StringBuilder errorMessage = new StringBuilder();

    //         for (FieldError error : bindingResult.getFieldErrors()) {
    //             String defaultMessage = error.getDefaultMessage();
    //             errorMessage.append(defaultMessage).append("<br>");
    //         }

    //         redirectAttributes.addFlashAttribute("error", errorMessage);
    //         return new RedirectView("/register");
    //     }

    //     try {
    //         String uri = "http://localhost:8081/api/seller/create";
    //         RestTemplate restTemplate = new RestTemplate();
    //         restTemplate.postForEntity(uri, userRequestDTO, CreateUserRequestDTO.class);

    //         // Registration successful, add a success message
    //         redirectAttributes.addFlashAttribute("success", "Registration successful!");
    //     } catch (Exception e) {
    //         // Registration failed, add an error message
    //         redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
    //     }

    //     return new RedirectView("/");
    // }

    @GetMapping("login")
    public String formLogin(Model model){
        var user  = new LoginJwtRequestDTO();
        model.addAttribute("userDTO", user);
        return "user/login";
    }

    // @PostMapping("login")
    // public RedirectView formLogin(@Valid @ModelAttribute LoginJwtRequestDTO loginJwtRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
    //     if (bindingResult.hasErrors()) {
    //         StringBuilder errorMessage = new StringBuilder(); //Menginisiasi error message

    //         //Mengambil setiap error message yang ada
    //         for (FieldError error : bindingResult.getFieldErrors()) {
    //             String defaultMessage = error.getDefaultMessage();
    //             errorMessage.append(defaultMessage).append("<br>"); //Menampilkan error message dengan tampilan ke bawah
    //         }

    //         redirectAttributes.addFlashAttribute("error", errorMessage);
    //         return new RedirectView("/login");
    //     }
    //     String uri = "http://localhost:8081/api/login";
    //     RestTemplate restTemplate = new RestTemplate();
    //     ResponseEntity<LoginJwtRequestDTO> res = restTemplate.postForEntity(uri, loginJwtRequestDTO, LoginJwtRequestDTO.class);

    //     return new RedirectView("/");
    // }
}
