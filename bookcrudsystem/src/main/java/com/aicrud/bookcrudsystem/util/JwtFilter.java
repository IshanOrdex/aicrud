package com.aicrud.bookcrudsystem.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aicrud.bookcrudsystem.dao.UserDAO;
import com.aicrud.bookcrudsystem.entity.Users;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDAO userDAO;
    
	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	        String authorizationHeader = request.getHeader("Authorization");

	        String token = null;
	        String email = null;

	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            token = authorizationHeader.substring(7);
	            email = jwtUtil.extractEmail(token);
	        }

	        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            Users user = userDAO.findByEmail(email);

	            if (user != null && jwtUtil.validateToken(token, user.getEmail())) {
	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	            
	            if (user != null && jwtUtil.validateToken(token, user.getEmail())) {
	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(user, null,
	                                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleType().toUpperCase())));
	                SecurityContextHolder.getContext().setAuthentication(authToken);
	            }
	        }

	        filterChain.doFilter(request, response);
	    }
	

}
