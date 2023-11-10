package com.apapedia.user.restservice;

import java.util.UUID;

import com.apapedia.user.model.User;

public interface UserRestService {
    void createRestUser(User user);
    User getRestUserById(UUID id);
}
