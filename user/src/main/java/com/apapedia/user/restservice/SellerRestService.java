package com.apapedia.user.restservice;

import com.apapedia.user.model.Seller;

import java.util.UUID;
import java.util.List;

public interface SellerRestService {
    void createRestSeller(Seller seller);
    boolean withdraw(UUID idSeller, Long amount);
    Seller getSellerbyId(UUID id);
    List<Seller> retrieveAllSeller();
    boolean topUp(UUID idSeller, Long amount);
}
