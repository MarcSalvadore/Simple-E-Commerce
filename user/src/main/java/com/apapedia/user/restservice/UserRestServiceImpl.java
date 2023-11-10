package com.apapedia.user.restservice;

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
    public User getRestUserById(UUID id) {
        return userDb.findById(id).get();
    }
}
