package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Role;
import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerRestServiceImpl implements SellerRestService {
    @Autowired
    SellerDb sellerDb;
    
    @Autowired
    UserRestService userRestService;
    
    public void createRestSeller(Seller seller) {
        seller.setRole(Role.SELLER);
        String hashedPass = userRestService.encrypt(seller.getPassword());
        seller.setPassword(hashedPass);
        sellerDb.save(seller); 
    }
}
