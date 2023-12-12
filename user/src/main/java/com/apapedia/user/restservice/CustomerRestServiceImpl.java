package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.EnumRole;
import com.apapedia.user.repository.CustomerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerRestServiceImpl implements CustomerRestService {
    @Autowired
    CustomerDb customerDb;

    @Autowired
    UserRestService userRestService;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void createRestCustomer(Customer customer) { 
        customer.setRole(EnumRole.CUSTOMER);
        String hashedPass = encoder.encode(customer.getPassword());
        customer.setPassword(hashedPass);
        customerDb.save(customer); 
    }

}
