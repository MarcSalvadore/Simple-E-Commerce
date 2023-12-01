package com.apapedia.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.apapedia.user.security.jwt.JwtTokenFilter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired 
    UserDetailsService userDetailsService;
    
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    
    @Bean
    @Order(1)
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/seller/create", "/api/customer/create", "/api/login").permitAll()
                // .requestMatchers("/api/login").permitAll()
                .requestMatchers("/api/seller/**").hasAuthority("SELLER")
                .requestMatchers("/api/customer/**").hasAuthority("CUSTOMER")
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
            return http.build();
    }

    // @Bean
    // @Order(2)
    // public SecurityFilterChain webfilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(Customizer.withDefaults())
    //         .authorizeHttpRequests(requests -> requests
    //             .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
    //             .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
    //             .anyRequest().authenticated()
    //         )
    //         .formLogin((form) -> form 
    //             .loginPage("http://localhost:8084/login")
    //             .permitAll()
    //             .defaultSuccessUrl("http://localhost:8084/login/")
    //         )
    //         // .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    //         //     .logoutSuccessUrl("/login"))

    //     ;
    //     return http.build();
    // }
    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
