package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Role;
import com.apapedia.user.repository.CustomerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerRestServiceImpl implements CustomerRestService {
    @Autowired
    CustomerDb customerDb;

    @Autowired
    UserRestService userRestService;

    @Override
    public void createRestCustomer(Customer customer) { 
        customer.setRole(Role.CUSTOMER);
        String hashedPass = userRestService.encrypt(customer.getPassword());
        customer.setPassword(hashedPass);
        customerDb.save(customer); 
    }
}
