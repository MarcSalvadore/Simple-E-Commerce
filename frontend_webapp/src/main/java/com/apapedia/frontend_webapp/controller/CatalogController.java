package com.apapedia.frontend_webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apapedia.frontend_webapp.dto.request.CreateCatalogRequestDTO;
import com.apapedia.frontend_webapp.dto.request.UpdateCatalogRequestDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCatalogResponseDTO;
import com.apapedia.frontend_webapp.dto.response.ReadCategoryResponseDTO;
import com.apapedia.frontend_webapp.security.jwt.JwtUtils;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class CatalogController {
    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(CatalogController.class);

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    public CatalogController(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(Setting.SERVER_CATALOG_URL)
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
    }

    @GetMapping("add-product")
    public String formAddProduct(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String jwtToken = (String) session.getAttribute("token");

            if (jwtUtils.validateToken(jwtToken)) {
                String username = userService.getUsernameFromToken(jwtToken);
                model.addAttribute("username", username);
            }
        }

        var productDTO = new CreateCatalogRequestDTO();
        ReadCategoryResponseDTO[] listCategory = this.webClient
            .get()
            .uri("/api/category/viewall")
            .retrieve()
            .bodyToMono(ReadCategoryResponseDTO[].class)
            .block();

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("listCategory", listCategory);
        return "catalog/form-add-product";
    }
    
    @PostMapping("add-product")
    public String addProduct(@Valid @ModelAttribute CreateCatalogRequestDTO productRequestDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) throws IOException{
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");
        
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder(); // Menginisiasi error message

            // Mengambil setiap error message yang ada
            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>"); // Menampilkan error message dengan tampilan ke
                                                                    // bawah
            }

            redirectAttributes.addFlashAttribute("productDTO", productRequestDTO);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/add-product";
        }

        productRequestDTO.setSeller(userService.getUserIdFromToken(jwtToken));
        productRequestDTO.setImage(productRequestDTO.getImageFile().getBytes());

        var response = this.webClient
            .post()
            .uri("/api/catalog/add")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(productRequestDTO)
            .retrieve()
            .bodyToMono(CreateCatalogRequestDTO.class)
            .block();

        redirectAttributes.addFlashAttribute("success", "Produk telah ditambahkan");
        redirectAttributes.addFlashAttribute("productDTO", response);
        return "redirect:/";
    }

    @GetMapping("update-product/{idCatalog}")
    public String formUpdateProduct(@PathVariable UUID idCatalog, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String jwtToken = (String) session.getAttribute("token");

            if (jwtUtils.validateToken(jwtToken)) {
                String username = userService.getUsernameFromToken(jwtToken);
                model.addAttribute("username", username);
            }
        }

        ReadCatalogResponseDTO catalog = this.webClient
            .get()
            .uri("/api/catalog/detail")
            .retrieve()
            .bodyToMono(ReadCatalogResponseDTO.class)
            .block();

        var productDTO = new UpdateCatalogRequestDTO();

        productDTO.setProductName(catalog.getProductName());
        productDTO.setPrice(catalog.getPrice());
        productDTO.setProductDescription(catalog.getProductDescription());
        productDTO.setStock(catalog.getStock());

        ReadCategoryResponseDTO[] listCategory = this.webClient
            .get()
            .uri("/api/category/viewall")
            .retrieve()
            .bodyToMono(ReadCategoryResponseDTO[].class)
            .block();

        model.addAttribute("productDTO", productDTO);
        model.addAttribute("listCategory", listCategory);
        return "catalog/form-update-product";
    }

    @PostMapping("update-product")
    public String updateProduct(
            @PathVariable UUID idCatalog,
            @Valid @ModelAttribute UpdateCatalogRequestDTO productRequestDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession(false);
        String jwtToken = (String) session.getAttribute("token");;
        System.out.println(productRequestDTO);
        

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();

            for (FieldError error : bindingResult.getFieldErrors()) {
                String defaultMessage = error.getDefaultMessage();
                errorMessage.append(defaultMessage).append("<br>");
            }

            redirectAttributes.addFlashAttribute("productDTO", productRequestDTO);
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/update-product/" + idCatalog;
        }

        productRequestDTO.setId(idCatalog);
        productRequestDTO.setSeller(userService.getUserIdFromToken(jwtToken));
        productRequestDTO.setImage(productRequestDTO.getImageFile().getBytes());


        String uri = Setting.SERVER_CATALOG_URL + "/api/catalog/update";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(uri, productRequestDTO);

        redirectAttributes.addFlashAttribute("success", "Produk telah diperbarui");
        redirectAttributes.addFlashAttribute("productDTO", productRequestDTO);
        return "redirect:/";
    }
}
