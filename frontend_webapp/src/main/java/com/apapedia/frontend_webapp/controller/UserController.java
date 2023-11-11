package com.apapedia.frontend_webapp.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.apapedia.frontend_webapp.dto.SellerDTO;

import jakarta.validation.Valid;

@Controller
public class UserController {
    
    @GetMapping("/register")
    public String register(Model model){
        return "user/register";
    }

    private SellerDTO getData(String name, String username, String email, String password, String address){
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.add("name", name);
        formParams.add("username", username);
        formParams.add("password", password);
        formParams.add("email", email);
        formParams.add("address", address);

        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formParams, headers);

        HttpEntity<SellerDTO> responseEntity = restTemplate.exchange(
           "http://localhost:8081/seller/create",
           HttpMethod.POST,
           httpEntity,
           SellerDTO.class
        );
        SellerDTO responseBody = responseEntity.getBody();
        return responseBody;
    }

    @PostMapping(value = "/register")
    private SellerDTO registerSeller(@Valid @RequestBody SellerDTO sellerDTO, BindingResult bindingResult){
        SellerDTO seller = getData(sellerDTO.getName(), sellerDTO.getUsername(), sellerDTO.getEmail(), sellerDTO.getPassword(), sellerDTO.getAddress());
        buku.setJudul(translatedTitle.getData().getTranslatedText());
        bukuRestService.updateRestBuku(buku);

        return buku;
    }
}
