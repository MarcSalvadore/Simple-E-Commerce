// package com.apapedia.user.service;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.apapedia.user.model.Role;
// import com.apapedia.user.repository.RoleDb;

// @Service
// public class RoleServiceImpl implements RoleService{
//     @Autowired
//     private RoleDb roleDb;
    
//     @Override
//     public List<Role> getAllList() {
//        return roleDb.findAll();
//     }

//     @Override
//     public Role getRoleByRoleName(String name) {
//         Optional<Role> role = roleDb.findByRole(name);
//         if (role.isPresent()) {
//             return role.get();
//         }
//         return null;
//     }
    
// }
