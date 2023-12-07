package com.apapedia.user.restservice;

import java.util.List;
import java.util.UUID;


import com.apapedia.user.dto.request.LoginJwtRequestDTO;
import com.apapedia.user.model.UserModel;

public interface UserRestService {
    void createRestUser(UserModel user);
    List<UserModel> retrieveAllUser();
    UserModel getUserById(UUID id);
    UserModel updateRestUser(UserModel user);
    UserModel getUserByUsername(String username);
    String loginSeller(LoginJwtRequestDTO loginJwtRequestDTO);
    void deleteSeller(UserModel userModel);
}
