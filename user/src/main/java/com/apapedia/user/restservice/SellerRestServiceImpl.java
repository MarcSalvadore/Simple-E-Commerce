package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.EnumRole;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerRestServiceImpl implements SellerRestService {
    @Autowired
    SellerDb sellerDb;

    @Autowired
    UserDb userDb;

    @Override
    public void createRestSeller(Seller seller) { 
        seller.setRole(EnumRole.SELLER);
        String hashedPass = encrypt(seller.getPassword());
        seller.setPassword(hashedPass);

        Seller existingDeletedSellerByUsername = userDb.findByUsernameAndIsDeleted(seller.getUsername(), true);
        Seller existingDeletedSellerByEmail = userDb.findByEmailAndIsDeleted(seller.getEmail(), true);

        if (existingDeletedSellerByUsername != null) {
            Seller existingSeller = existingDeletedSellerByUsername;
            existingSeller.setIsDeleted(false);
            existingSeller.setEmail(seller.getEmail());
            existingSeller.setPassword(hashedPass); 
            sellerDb.save(existingSeller);

        } else if (existingDeletedSellerByEmail != null) {
            Seller existingSeller = existingDeletedSellerByEmail;
            existingSeller.setIsDeleted(false);
            existingSeller.setUsername(seller.getUsername());
            existingSeller.setPassword(hashedPass); 
            sellerDb.save(existingSeller);

        } else {
            sellerDb.save(seller);
        }
    }

    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
