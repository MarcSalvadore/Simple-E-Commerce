package com.apapedia.user.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.UserModel;
import com.apapedia.user.repository.UserDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    UserDb userDb;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void createRestUser(UserModel user) { userDb.save(user); }

    @Override
    public List<UserModel> retrieveAllUser() { return userDb.findAll(); }

    @Override
    public UserModel getUserById(UUID id) {
        for(UserModel user : retrieveAllUser()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public UserModel updateRestUser(UserModel userFromDto) {
        UserModel user = getUserById(userFromDto.getId());

        if(user != null) {
            user.setName(userFromDto.getName());
            user.setName(userFromDto.getUsername());
            user.setEmail(userFromDto.getEmail());
            user.setPassword(userFromDto.getPassword());
            user.setAddress(userFromDto.getAddress());

            userDb.save(user);
        }

        return user;
    }

    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    } 
}
