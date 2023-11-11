package com.apapedia.user.restservice;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apapedia.user.model.User;
import com.apapedia.user.repository.UserDb;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserRestServiceImpl implements UserRestService {
    @Autowired
    UserDb userDb;

    @Override
    public void createRestUser(User user) { userDb.save(user); }

    @Override
    public List<User> retrieveAllUser() { return userDb.findAll(); }

    @Override
    public User getUserById(UUID id) {
        for(User user : retrieveAllUser()) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User updateRestUser(User userFromDto) {
        User user = getUserById(userFromDto.getId());

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
}
