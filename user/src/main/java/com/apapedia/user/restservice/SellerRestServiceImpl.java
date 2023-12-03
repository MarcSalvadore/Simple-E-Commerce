package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.EnumRole;
import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerRestServiceImpl implements SellerRestService {
    @Autowired
    SellerDb sellerDb;

    @Override
    public void createRestSeller(Seller seller) { 
        seller.setRole(EnumRole.SELLER);
        String hashedPass = encrypt(seller.getPassword());
        seller.setPassword(hashedPass);
        sellerDb.save(seller); 
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
