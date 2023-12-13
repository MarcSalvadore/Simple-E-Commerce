package com.apapedia.user.restcontroller;

import com.apapedia.user.dto.request.ReadTopUpRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
<<<<<<< HEAD
import org.springframework.http.MediaType;
=======
>>>>>>> 67a2857 (test commit)
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD

import com.apapedia.user.dto.SellerMapper;
import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.response.ReadWithdrawResponseDTO;
=======
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.SellerMapper;
import com.apapedia.user.dto.request.CreateSellerRequestDTO;
import com.apapedia.user.dto.response.SellerResponseDTO;
import com.apapedia.user.model.Seller;
>>>>>>> 67a2857 (test commit)
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

<<<<<<< HEAD
    @PostMapping(value = "/seller/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> restAddSeller(@Valid @RequestBody CreateUserRequestDTO sellerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
                return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);
        } else { 
            var seller = sellerMapper.createSellerRequestDTOToSeller(sellerDTO);
            sellerRestService.createRestSeller(seller);
            return ResponseEntity.ok("Registrasi berhasil!");
=======
    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping(value = "/seller/create")
    public ResponseEntity<SellerResponseDTO> restAddSeller (@Valid @RequestBody CreateSellerRequestDTO sellerDTO, BindingResult bindingResult) {
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
            return 
>>>>>>> 67a2857 (test commit)
        }
    }

    @PostMapping(value = "/withdraw")
    public String restWithdraw(@RequestBody ReadWithdrawResponseDTO withdrawResponseDTO) {
        boolean res = sellerRestService.withdraw(withdrawResponseDTO.getIdSeller(), withdrawResponseDTO.getAmount());
        
        if (res) {
            return "Withdraw berhasil!";
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
