package com.apapedia.user.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.SellerMapper;
import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.response.SellerResponseDTO;
import com.apapedia.user.model.Seller;
import com.apapedia.user.restservice.SellerRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class SellerRestController {
   @Autowired
    SellerMapper sellerMapper;

    @Autowired
    SellerRestService sellerRestService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping(value = "/seller/create")
    public Seller restAddSeller(@Valid @RequestBody CreateUserRequestDTO sellerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {  
            var seller = sellerMapper.createSellerRequestDTOToSeller(sellerDTO);
            sellerRestService.createRestSeller(seller);
            SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
            sellerResponseDTO.setName(sellerDTO.getName());
            sellerResponseDTO.setUsername(sellerDTO.getUsername());
            sellerResponseDTO.setPassword(sellerDTO.getPassword());
            sellerResponseDTO.setEmail(sellerDTO.getEmail());
            sellerResponseDTO.setAddress(sellerDTO.getAddress());
            return seller;
        }
    }
}
