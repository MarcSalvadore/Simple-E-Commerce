package com.apapedia.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.user.model.Customer;
import com.apapedia.user.model.Seller;
import com.apapedia.user.model.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
    UserModel findByEmail(String email);
    Seller findSellerByUsernameAndIsDeleted(String username, boolean isDeleted);
    Seller findSellerByEmailAndIsDeleted(String email, boolean isDeleted);
    Customer findCustomerByUsernameAndIsDeleted(String username, boolean isDeleted);
    Customer findCustomerByEmailAndIsDeleted(String email, boolean isDeleted);
}
