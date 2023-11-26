package com.apapedia.user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.user.model.UserModel;

@Repository
public interface UserDb extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
