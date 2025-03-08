package com.aicrud.bookcrudsystem.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Allow registration and login without authentication
//            .anyRequest().authenticated() // All other endpoints require authentication
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Stateless session for REST APIs
//
//        return http.build();
//    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) 
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/rest/user/register", "/rest/user/login").permitAll() 
                .anyRequest().authenticated() 
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        return http.build();
    }
	
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	
}
