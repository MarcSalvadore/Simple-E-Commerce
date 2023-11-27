package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.EnumRole;
import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.service.AuthService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerRestServiceImpl implements SellerRestService {
    @Autowired
    SellerDb sellerDb;

    @Autowired
    AuthService authService;

    public void createRestSeller(Seller seller) { 
        seller.setRole(EnumRole.SELLER);
        String hashedPass = authService.encrypt(seller.getPassword());
        seller.setPassword(hashedPass);
        sellerDb.save(seller); 
    }
}
