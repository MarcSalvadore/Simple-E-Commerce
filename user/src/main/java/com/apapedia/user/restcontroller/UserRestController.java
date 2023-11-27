package com.apapedia.user.restcontroller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.UserMapper;
import com.apapedia.user.dto.request.UpdateUserRequestDTO;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.restservice.UserRestService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    UserRestService userRestService;

    @Autowired
    UserMapper userMapper;
    
    @GetMapping(value = "/user/{id}")
    private UserModel getUser(@PathVariable("id") UUID id){
        try {
            return userRestService.getUserById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }
    
    @PutMapping(value = "user/update")
    private UserModel updateUser(@Valid @RequestBody UpdateUserRequestDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");      
        } else {
            UserModel userFromDto = userMapper.updateUserRequestDTOToUser(userDTO);
            UserModel user = userRestService.updateRestUser(userFromDto);
            return user;
        }
    }
}

