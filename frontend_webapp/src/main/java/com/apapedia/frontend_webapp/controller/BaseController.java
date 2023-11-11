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

import com.apapedia.frontend_webapp.dto.request.CreateProductRequestDTO;

import jakarta.validation.Valid;

@Controller
public class BaseController {
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("add-product")
    public String formAddProduct(Model model) {
        var productDTO = new CreateProductRequestDTO();

        model.addAttribute("productDTO", productDTO);
        return "form-add-product";
    }

    @PostMapping("add-product")
    public RedirectView addProduct(@Valid @ModelAttribute CreateProductRequestDTO productRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(); //Menginisiasi error message

            //Mengambil setiap error message yang ada
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); //Menampilkan error message dengan tampilan ke bawah
            }

            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return new RedirectView("/");
        }

        String uri = "http://localhost:8081/api/seller/add_product";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CreateProductRequestDTO> res = restTemplate.postForEntity(uri, productRequestDTO, CreateProductRequestDTO.class);

        return new RedirectView("/");
    }
}
