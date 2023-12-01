package com.apapedia.user.restcontroller;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.apapedia.user.dto.UserMapper;
import com.apapedia.user.dto.request.LoginJwtRequestDTO;
import com.apapedia.user.dto.request.UpdateUserRequestDTO;
import com.apapedia.user.dto.response.CurrentUserResponseDTO;
import com.apapedia.user.dto.response.LoginJwtResponseDTO;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.restservice.UserRestService;
import com.apapedia.user.security.UserDetailsServiceImpl;
import com.apapedia.user.security.jwt.JwtUtils;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    UserRestService userRestService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtUtils jwtUtils;

    // private static UserModel getCurrentUser() {
    //     return ((UserModel) SecurityContextHolder.getContext()
    //             .getAuthentication()
    //             .getPrincipal());
    // }

    // @GetMapping("/current")
    // @PreAuthorize("hasAuthority('user:read')")
    // public ResponseEntity<CurrentUserResponseDTO> getCurrentUserProfile() {
    //     CurrentUserResponseDTO response = userRestService.getCurrentUser(getCurrentUser().getUsername());
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }
    
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
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginJwtRequestDTO loginJwtRequestDTO) throws Exception{
        try {
            authenticate(loginJwtRequestDTO.getUsername(), loginJwtRequestDTO.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginJwtRequestDTO.getUsername());
            final String token = jwtUtils.generateJwtToken(userDetails.getUsername());
            LoginJwtResponseDTO res = new LoginJwtResponseDTO();
            res.setToken(token);
            return ResponseEntity.ok(res);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}

