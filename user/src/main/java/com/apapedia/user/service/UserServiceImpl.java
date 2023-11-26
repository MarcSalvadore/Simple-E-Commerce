// package com.apapedia.user.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import com.apapedia.user.dto.request.CreateUserRequestDTO;
// import com.apapedia.user.dto.request.LoginJwtRequestDTO;
// import com.apapedia.user.model.UserModel;
// import com.apapedia.user.repository.UserDb;
// import com.apapedia.user.security.jwt.JwtUtils;

// @Service
// public class UserServiceImpl implements UserService{
//     @Autowired
//     private UserDb userDb;

//     @Autowired
//     private RoleService roleService;

//     @Autowired
//     private JwtUtils jwtUtils;

//     @Override
//     public UserModel addUser(UserModel user, CreateUserRequestDTO createUserRequestDTO) {
//         user.setRole(roleService.getRoleByRoleName(createUserRequestDTO.getRole()));
//         String hashedPass = encrypt(user.getPassword());
//         user.setPassword(hashedPass);
//         return userDb.save(user);
//     }

//     @Override
//     public String encrypt(String password) {
//         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//         return passwordEncoder.encode(password);
//     }

//     @Override
//     public String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO) {
//         String username = loginJwtRequestDTO.getUsername();
//         String name = loginJwtRequestDTO.getName();

//         UserModel user = userDb.findByUsername(username);

//         if (user == null) {
//             user = new UserModel();
//             user.setName(name);
//             user.setPassword("bacabaca");
//             user.setUsername(username);
//             user.setRole(roleService.getRoleByRoleName("Admin"));
//             userDb.save(user);
//         }

//         return jwtUtils.generateJwtToken(loginJwtRequestDTO.getUsername());
//     }

    
// }
