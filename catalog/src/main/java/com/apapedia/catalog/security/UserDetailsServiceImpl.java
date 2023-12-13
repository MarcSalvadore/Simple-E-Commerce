// package com.apapedia.catalog.security;

// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
// // import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.reactive.function.client.WebClient;

// @Service
// public class UserDetailsServiceImpl implements UserDetailsService{
//     private final WebClient webClient;

//     public UserDetailsServiceImpl(WebClient.Builder webClientBuilder){
//         this.webClient = webClientBuilder.baseUrl("http://localhost:8081")
//                     .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                     .build();
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         if (getUserByUsername(username, username)) {
//             //    String usernameFromDb = username -> boolean
//             //    if true 
//             //    set grantedauthority
//         }

//     }

//     protected boolean getUserByUsername (String username, String jwtToken) {
//         var response = this.webClient
//                 .get()
//                 .uri("user/{username}", username)
//                 .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
//                 .retrieve()
//                 .bodyToMono(Boolean.class)
//                 .block();
//         return response;
//     }

// }
