package com.apapedia.user.service;

import com.apapedia.user.dto.request.CreateUserRequestDTO;
import com.apapedia.user.dto.request.LoginJwtRequestDTO;
import com.apapedia.user.model.UserModel;

public interface UserService {
    UserModel addUser(UserModel user, CreateUserRequestDTO createUserRequestDTO);
    String encrypt(String password);
    String loginJwtAdmin(LoginJwtRequestDTO loginJwtRequestDTO);
}
