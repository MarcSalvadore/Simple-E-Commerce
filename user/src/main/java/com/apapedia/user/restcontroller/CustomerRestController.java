package com.apapedia.user.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.CustomerMapper;
import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.response.CartResponseDTO;
import com.apapedia.user.dto.response.CustomerResponseDTO;
import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Role;
import com.apapedia.user.restservice.CustomerRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CustomerRestController {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerRestService customerRestService;

    @PostMapping(value = "/customer/create")
    public Customer restAddCustomer(@Valid @RequestBody CreateUserRequestDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var customer = customerMapper.createUserRequestDTOToCustomer(customerDTO);
            CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
            customerResponseDTO.setId(customer.getCartId());
            customerResponseDTO.setUserId(customer.getId());
            String uri = "http://localhost:8083/api/cart/create";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CartResponseDTO> res = restTemplate.postForEntity(uri, customerResponseDTO, CartResponseDTO.class);
            customerRestService.createRestCustomer(customer);
            return customer;
        }
    }
}
