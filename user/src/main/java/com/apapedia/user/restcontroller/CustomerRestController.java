package com.apapedia.user.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.CustomerMapper;
import com.apapedia.user.dto.request.CreateCustomerRequestDTO;
import com.apapedia.user.model.Customer;
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
    public Customer restAddCustomer(@Valid @RequestBody CreateCustomerRequestDTO customerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field"
            );
        } else {
            var customer = customerMapper.createCustomerRequestDTOToCustomer(customerDTO);
            customerRestService.createRestCustomer(customer);
            return customer;
        }
    }
}
