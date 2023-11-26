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
public class UserController {
    
    @GetMapping("register")
    public String formRegister(Model model){
        var user = new CreateUserRequestDTO();

        model.addAttribute("userDTO", user);
        return "user/register";
    }

    @PostMapping("register")
    private RedirectView registerSeller(@Valid @ModelAttribute CreateUserRequestDTO userRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        //Validasi gagal, kembalikan pesan error
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(); //Menginisiasi error message

            //Mengambil setiap error message yang ada
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); //Menampilkan error message dengan tampilan ke bawah
            }

            redirectAttributes.addFlashAttribute("error", errorMessage);
            return new RedirectView("/register");
        }

        // disini kasih if else kalo username/password/email udah ada tp ntar aja dah

        String uri = "http://localhost:8081/api/seller/create";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateUserRequestDTO> res = restTemplate.postForEntity(uri, userRequestDTO, CreateUserRequestDTO.class);

        return new RedirectView("/");
    }

    @GetMapping("login")
    public String formLogin(Model model){
        // var user = new CreateUserRequestDTO();

        // model.addAttribute("userDTO", user);
        return "user/login";
    }
    // @PostMapping("login")
}
