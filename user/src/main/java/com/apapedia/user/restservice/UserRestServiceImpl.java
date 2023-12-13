package com.apapedia.user.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.dto.request.LoginJwtRequestDTO;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.security.jwt.JwtUtils;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    UserDb userDb;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Override
    public void createRestUser(UserModel user) { userDb.save(user); }

    @Override
    public List<UserModel> retrieveAllUser() { return userDb.findAll(); }

    @Override
    public UserModel getUserById(UUID id) {
        for(UserModel user : retrieveAllUser()) {
            if (user.getId().equals(id) && user.getIsDeleted() != true) {
                return user;
            }
        }
        return null;
    }

    @Override
    public UserModel updateRestUser(UserModel userFromDto) {
        UserModel user = getUserById(userFromDto.getId());

        if (user != null) {
            if (!isPasswordSame(userFromDto.getPassword(), user.getPassword())) {
                user.setName(userFromDto.getName());
                user.setUsername(userFromDto.getUsername());
                user.setEmail(userFromDto.getEmail());
                user.setAddress(userFromDto.getAddress());
    
                if (!encoder.matches(userFromDto.getPassword(), user.getPassword())) {
                    user.setPassword(encoder.encode(userFromDto.getPassword()));
                }
    
                userDb.save(user);
                return user;
            } else {
                throw new IllegalArgumentException("New password must be different from the old password");
                
            }
        }
        return null; 
        
        
    }

    @Override
    public UserModel getUserByUsername(String username) {
        for(UserModel user : retrieveAllUser()) {
            if (user.getUsername().equals(username) && user.getIsDeleted() != true) {
                return user;
            }
        }
        return null;
    }

    @Override
    public String loginSeller(LoginJwtRequestDTO loginJwtRequestDTO) {
        String username = loginJwtRequestDTO.getUsername();
        UserModel user = userDb.findByUsername(username);
        if (user == null || user.getIsDeleted() == true) {
            throw new UsernameNotFoundException("User not found. Please register user.");
        }
        return jwtUtils.generateJwtToken(user.getId(), username, user.getRole().toString());
    }

    @Override
    public void deleteSeller(UserModel userModel) {
       userModel.setIsDeleted(true);
       userDb.save(userModel);
    }
    
    private boolean isPasswordSame(String newPassword, String existingPassword) {
        return encoder.matches(newPassword, existingPassword);
    }

    @Override
    public boolean isCurrentPasswordCorrect(UUID userId, String currentPassword) {
        UserModel user = userDb.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
                return encoder.matches(currentPassword, user.getPassword());
    }

    @Override
    public void changeUserPassword(UUID userId, String newPassword) {
        UserModel user = userDb.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        String hashedPassword = encoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userDb.save(user);
    }
}
