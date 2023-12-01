package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.EnumRole;
import com.apapedia.user.repository.CustomerDb;
import com.apapedia.user.service.AuthService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerRestServiceImpl implements CustomerRestService {
    @Autowired
    CustomerDb customerDb;

    @Autowired
    AuthService authService;

    @Override
    public void createRestCustomer(Customer customer) { 
        customer.setRole(EnumRole.CUSTOMER);
        String hashedPass = authService.encrypt(customer.getPassword());
        customer.setPassword(hashedPass);
        customerDb.save(customer); 
    }

}
