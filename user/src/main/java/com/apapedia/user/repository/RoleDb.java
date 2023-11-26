package com.apapedia.user.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apapedia.user.model.Role;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDb extends JpaRepository<Role, UUID> {
    List<Role> findAll();
    Optional<Role> findByRole(String name);
}
