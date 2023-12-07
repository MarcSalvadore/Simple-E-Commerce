package com.apapedia.frontend_webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.apapedia.frontend_webapp.dto.request.CreateUserRequestDTO;
import com.apapedia.frontend_webapp.dto.request.LoginJwtRequestDTO;
import com.apapedia.frontend_webapp.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private RedirectView registerSeller(@Valid @ModelAttribute CreateUserRequestDTO userRequestDTO, RedirectAttributes redirectAttributes){
        try {
            String uri = "http://localhost:8081/api/seller/create";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(uri, userRequestDTO, String.class);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
        } catch (Exception e) {
            System.out.println(e.toString());
            redirectAttributes.addFlashAttribute("error", "Registration failed. Please try again.");
        }

        return new RedirectView("/login");
    }

    @GetMapping("login")
    public String formLogin(Model model){
        var user  = new LoginJwtRequestDTO();
        model.addAttribute("userDTO", user);
        return "user/login";
    }

    // @PostMapping("login")
    // public RedirectView formLogin(@Valid @ModelAttribute LoginJwtRequestDTO loginJwtRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes,HttpServletResponse response){
    //     try {
    //         String token = userService.getToken(loginJwtRequestDTO.getUsername(), loginJwtRequestDTO.getPassword());
    //         Cookie cookie = new Cookie("token", token);
    //                 cookie.setPath("/");
    //                 response.addCookie(cookie);

    //         String uri = "http://localhost:8081/api/login";
    //         RestTemplate restTemplate = new RestTemplate();
    //         ResponseEntity<String> res = restTemplate.postForEntity(uri, loginJwtRequestDTO, String.class);

    //         return new RedirectView("/");

    //     } catch (Exception e) {
    //         System.out.println(e.toString());
    //         redirectAttributes.addFlashAttribute("error", "Login failed. Please try again.");
    //         return new RedirectView("/login");
    //     }
        
    // }

    @PostMapping("/logout")
    public RedirectView logout(@CookieValue(name = "token", required = false) String token, HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new RedirectView("/");
    }

    
    
}
