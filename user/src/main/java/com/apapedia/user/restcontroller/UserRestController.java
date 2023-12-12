package com.apapedia.user.restcontroller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.apapedia.user.dto.response.LoginCustomerResponseDTO;
import com.apapedia.user.dto.response.LoginJwtResponseDTO;
import com.apapedia.user.dto.response.UpdateUserResponseDTO;
import com.apapedia.user.model.UserModel;
import com.apapedia.user.restservice.UserRestService;
import com.apapedia.user.security.UserDetailsServiceImpl;
import com.apapedia.user.security.jwt.JwtUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

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

    //User details
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

    //Edit profile
    @PutMapping(value = "user/update")
    private UpdateUserResponseDTO updateUser(@Valid @RequestBody UpdateUserRequestDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Request body has invalid type or missing field");      
        } else {
            UserModel userFromDto = userMapper.updateUserRequestDTOToUser(userDTO);
            UserModel user = userRestService.updateRestUser(userFromDto);
            UpdateUserResponseDTO userResponseDTO = userMapper.userToUpdateUserResponseDTO(user);
            return userResponseDTO;
        }
    }

    //Login customer
    @PostMapping("/auth/login-customer")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginJwtRequestDTO loginJwtRequestDTO, BindingResult bindingResult) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginJwtRequestDTO.getUsername(), loginJwtRequestDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginJwtRequestDTO.getUsername());
            UserModel user = userRestService.getUserByUsername(userDetails.getUsername());


            String jwt = jwtUtils.generateJwtToken(user.getId(),loginJwtRequestDTO.getUsername(), user.getRole().toString());
            return ResponseEntity.ok(new LoginCustomerResponseDTO(jwt,user.getId().toString()));

        } catch (AuthenticationException e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "false");
            response.put("message", "Username atau password salah");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }

    //Login seller
    @PostMapping("/auth/login-seller")
    public ResponseEntity<?> loginSeller(@RequestBody LoginJwtRequestDTO loginJwtRequestDTO) {
        try {
            String jwtToken = userRestService.loginSeller(loginJwtRequestDTO);
            return new ResponseEntity<>(new LoginJwtResponseDTO(jwtToken), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "User not found"
            );
        }
    }

    //Delete user
    @DeleteMapping("user/{id}/delete")
    public ResponseEntity<String> deleteCatalog(@PathVariable("id") UUID id) {
        var seller = userRestService.getUserById(id);
        userRestService.deleteSeller(seller);
        return ResponseEntity.ok("User has been deleted");
    }
    
}

