package com.apapedia.user.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.SellerMapper;
import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.response.CreateUserResponseDTO;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.restservice.SellerRestService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class SellerRestController {
   @Autowired
    SellerMapper sellerMapper;

    @Autowired
    SellerRestService sellerRestService;

    @PostMapping(value = "/seller/create")
    public ResponseEntity<String> restAddSeller(@Valid @RequestBody CreateUserRequestDTO sellerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else { 
            var seller = sellerMapper.createSellerRequestDTOToSeller(sellerDTO);
            sellerRestService.createRestSeller(seller);
            log.info("Created new seller");
            return ResponseEntity.ok("Registrasi berhasil!");
        }
    }
}
