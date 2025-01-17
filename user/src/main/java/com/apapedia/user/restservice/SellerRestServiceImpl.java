package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.EnumRole;
import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;
import com.apapedia.user.repository.UserDb;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SellerRestServiceImpl implements SellerRestService {
    @Autowired
    SellerDb sellerDb;

    @Autowired
    UserDb userDb;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void createRestSeller(Seller seller) { 
        seller.setRole(EnumRole.SELLER);
        String hashedPass = encoder.encode(seller.getPassword());
        seller.setPassword(hashedPass);

        //Jika user sudah dihapus dan ingin mendaftar lagi dengan username atau email yang sama
        Seller existingDeletedSellerByUsername = userDb.findSellerByUsernameAndIsDeleted(seller.getUsername(), true);
        Seller existingDeletedSellerByEmail = userDb.findSellerByEmailAndIsDeleted(seller.getEmail(), true);

        if (existingDeletedSellerByUsername != null) {
            Seller existingSeller = existingDeletedSellerByUsername;
            existingSeller.setIsDeleted(false);
            existingSeller.setEmail(seller.getEmail());
            existingSeller.setPassword(hashedPass); 
            existingSeller.setName(seller.getName());
            existingSeller.setAddress(seller.getAddress());
            sellerDb.save(existingSeller);

        } else if (existingDeletedSellerByEmail != null) {
            Seller existingSeller = existingDeletedSellerByEmail;
            existingSeller.setIsDeleted(false);
            existingSeller.setUsername(seller.getUsername());
            existingSeller.setPassword(hashedPass); 
            existingSeller.setEmail(seller.getEmail());
            existingSeller.setAddress(seller.getAddress());
            existingSeller.setName(seller.getName());
            sellerDb.save(existingSeller);

        } else {
            sellerDb.save(seller);
        }
    }

    @Override
    public boolean withdraw(UUID idSeller, Long amount) {
        Seller seller = getSellerbyId(idSeller);

        if (seller != null) {
            Long balance = seller.getBalance();
            
            seller.setBalance(balance - amount);
            sellerDb.save(seller);
            
            return true;
        }

        return false;
    }

    @Override
    public Seller getSellerbyId(UUID id) {
        for (Seller seller : retrieveAllSeller()) {
            if (seller.getId().equals(id) && seller.getIsDeleted() == false) {
                return seller;
            }
        }

        return null;
    }

    @Override
    public List<Seller> retrieveAllSeller() { return sellerDb.findAll(); }

    @Override
    public boolean topUp(UUID idSeller, Long amount) {
        Seller seller = getSellerbyId(idSeller);

        if (seller != null) {
            Long balance = seller.getBalance();

            seller.setBalance(balance + amount);
            sellerDb.save(seller);

            return true;
        }

        return false;

    }
}
