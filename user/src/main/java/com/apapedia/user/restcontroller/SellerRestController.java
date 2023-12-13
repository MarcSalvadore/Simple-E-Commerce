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

import com.apapedia.user.dto.SellerMapper;
import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.response.ReadWithdrawResponseDTO;
import com.apapedia.user.restservice.SellerRestService;
import com.apapedia.user.restservice.UserRestService;

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

    @PostMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String restWithdraw(@RequestBody ReadWithdrawResponseDTO withdrawResponseDTO) {
        boolean res = sellerRestService.withdraw(withdrawResponseDTO.getIdSeller(), withdrawResponseDTO.getAmount());
        
        if (res) {
            return "Withdraw berhasil!";
        }

        return null;
    }
}
