package com.apapedia.user.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.Seller;
import com.apapedia.user.repository.SellerDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SellerRestServiceImpl implements SellerRestService {
    @Autowired
    SellerDb sellerDb;

    public void createRestSeller(Seller seller) { sellerDb.save(seller); }
}
