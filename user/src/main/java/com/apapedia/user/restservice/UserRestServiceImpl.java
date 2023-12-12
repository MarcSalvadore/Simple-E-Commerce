package com.apapedia.user.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apapedia.user.dto.request.LoginJwtRequestDTO;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.repository.UserDb;
import com.apapedia.user.security.jwt.JwtUtils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    UserDb userDb;

    @Autowired
    JwtUtils jwtUtils;

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
    public boolean updateRestUser(UserModel userFromDto) {
        UserModel user = getUserById(userFromDto.getId());
        System.out.println("INI USER DARI DB");
        System.out.println(user.getUsername());
        System.out.println(user);
        if(user != null) {
            System.out.println("MASUK IF");
            System.out.println(userFromDto.getName());
            user.setName(userFromDto.getName());
            user.setName(userFromDto.getUsername());
            user.setEmail(userFromDto.getEmail());
            user.setPassword(userFromDto.getPassword());
            user.setAddress(userFromDto.getAddress());

            userDb.save(user);

            return true;
        }

        System.out.println(user);
        return false;
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
    
}
