package com.apapedia.user.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apapedia.user.dto.SellerMapper;
import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.request.ReadTopUpRequestDTO;
import com.apapedia.user.dto.response.ReadWithdrawResponseDTO;
import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
import com.apapedia.user.restservice.SellerRestService;
import com.apapedia.user.restservice.UserRestService;
import com.github.javafaker.Bool;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class SellerRestController {
   @Autowired
    SellerMapper sellerMapper;

    @Autowired
    SellerRestService sellerRestService;

    @Autowired
    UserRestService userRestService;

    @PostMapping(value = "/seller/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> restAddSeller(@Valid @RequestBody CreateUserRequestDTO sellerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
                return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else { 
            var seller = sellerMapper.createSellerRequestDTOToSeller(sellerDTO);
            sellerRestService.createRestSeller(seller);
            return ResponseEntity.ok("Registrasi berhasil!");
        }
    }

    @PutMapping(value = "/withdraw/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> restWithdraw(@PathVariable("id") UUID id, @RequestBody ReadWithdrawResponseDTO withdrawResponseDTO) {
    
        var res = sellerRestService.withdraw(id, withdrawResponseDTO.getAmount());
        if (res) {
            return ResponseEntity.ok("Withdraw berhasil");
        }
        return null;
    }

    @PostMapping(value = "/topup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String restTopUp(@RequestBody ReadTopUpRequestDTO topUpRequestDTO) {
        boolean res = sellerRestService.topUp(topUpRequestDTO.getIdSeller(), topUpRequestDTO.getAmount());

        if (res) {
            return "Top-up berhasil!";
        }

        return null;
    }

}
