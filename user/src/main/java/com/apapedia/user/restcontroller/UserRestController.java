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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    // @GetMapping(value = "/user/{id}")
    // private ResponseEntity<CurrentUserResponseDTO> getUser(@PathVariable("id") UUID id){
    //     try {
    //         UserModel user = userRestService.getUserById(id);
    //         CurrentUserResponseDTO userDTO = new CurrentUserResponseDTO();
    //         userDTO.setId(user.getId());
    //         userDTO.setName(user.getName());
    //         userDTO.setUsername(user.getUsername());
    //         userDTO.setEmail(user.getEmail());
    //         user.setPassword(user.getPassword());
    //         user.setAddress(user.getAddress());
            
    //         return ResponseEntity.ok(userDTO);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(
    //             HttpStatus.NOT_FOUND, "User not found"
    //         );
    //     }
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
            UserModel user = userRestService.getUserByUsername(userDetails.getUsername());

            final String token = jwtUtils.generateJwtToken(user.getId(),user.getUsername(),user.getRole().toString());

            LoginJwtResponseDTO res = new LoginJwtResponseDTO();
            res.setToken(token);

            return ResponseEntity.ok(res);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username not found");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session or clear authentication token
        // Example using HttpSession:
        request.getSession().invalidate();

        // Clear the authentication token (if using tokens)
        // Example using cookies:
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok("Logout successful");
    }


}

