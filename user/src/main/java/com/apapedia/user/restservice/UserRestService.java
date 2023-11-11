package com.apapedia.user.restservice;

import java.util.List;
import java.util.UUID;

import com.apapedia.user.model.User;

public interface UserRestService {
    void createRestUser(User user);

    List<User> retrieveAllUser();

    User getUserById(UUID id);

    User updateRestUser(User user);
}
