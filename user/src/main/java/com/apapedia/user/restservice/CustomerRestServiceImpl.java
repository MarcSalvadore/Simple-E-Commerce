package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.EnumRole;
import com.apapedia.user.repository.CustomerDb;
import com.apapedia.user.repository.UserDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerRestServiceImpl implements CustomerRestService {
    @Autowired
    UserDb userDb;
    
    @Autowired
    CustomerDb customerDb;

    @Autowired
    UserRestService userRestService;

    @Override
    public void createRestCustomer(Customer customer) { 
        customer.setRole(EnumRole.CUSTOMER);
        String hashedPass = encrypt(customer.getPassword());
        customer.setPassword(hashedPass);

        //Jika user sudah dihapus dan ingin mendaftar lagi dengan username atau email yang sama
        Customer existingDeletedCustomerByUsername = userDb.findCustomerByUsernameAndIsDeleted(customer.getUsername(), true);
        Customer existingDeletedCustomerByEmail = userDb.findCustomerByEmailAndIsDeleted(customer.getEmail(), true);

        if (existingDeletedCustomerByUsername != null) {
            Customer existingCustomer = existingDeletedCustomerByUsername;
            existingCustomer.setIsDeleted(false);
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPassword(hashedPass); 
            existingCustomer.setName(customer.getName());
            existingCustomer.setAddress(customer.getAddress());
            customerDb.save(existingCustomer);

        } else if (existingDeletedCustomerByEmail != null) {
            Customer existingCustomer = existingDeletedCustomerByEmail;
            existingCustomer.setIsDeleted(false);
            existingCustomer.setUsername(customer.getUsername());
            existingCustomer.setPassword(hashedPass); 
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setAddress(customer.getAddress());
            existingCustomer.setName(customer.getName());
            customerDb.save(existingCustomer);

        } else {
            customerDb.save(customer);
        }
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
