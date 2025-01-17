// package com.apapedia.catalog.security;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.apapedia.catalog.security.jwt.JwtTokenFilter;

// @Configuration
// @EnableWebSecurity
// public class WebSecurityConfig {

//     @Autowired
//     JwtTokenFilter jwtTokenFilter;
    
//     @Bean
//     public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
//         http 
//             .securityMatcher("/api/**")
//             .csrf(AbstractHttpConfigurer::disable)
//             .authorizeHttpRequests(authorize -> authorize
//                 .requestMatchers("/api/seller/create", "/api/customer/create", "/api/login", "/api/auth/login-seller").permitAll()
//                 .anyRequest().authenticated()
//             )
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//             return http.build();
//     }
// }
