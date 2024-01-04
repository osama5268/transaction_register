package com.jar_assignment.kirana_transactions.filter;

import com.jar_assignment.kirana_transactions.service.JwtService; 
import com.jar_assignment.kirana_transactions.service.UserInfoService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain; 
import jakarta.servlet.ServletException; 
import jakarta.servlet.http.HttpServletRequest; 
import jakarta.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.context.SecurityContextHolder; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; 
import org.springframework.stereotype.Component; 
import org.springframework.web.filter.OncePerRequestFilter; 

import java.io.IOException; 

@Component
public class JwtAuthFilter extends OncePerRequestFilter { 

	@Autowired
	private JwtService jwtService; 

	@Autowired
	private UserInfoService userDetailsService; 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
		String authHeader = request.getHeader("Authorization"); 
		String token = null; 
		String username = null; 
        try{
            if (authHeader != null && authHeader.startsWith("Bearer ")) { 
                token = authHeader.substring(7); 
                username = jwtService.extractUsername(token); 
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
                
                    if (jwtService.validateToken(token, userDetails)) { 
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                    SecurityContextHolder.getContext().setAuthentication(authToken); 
                    }
            } 
            filterChain.doFilter(request, response); 
        }
        catch(ExpiredJwtException e){
            System.out.println("token expired");
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token has expired");
            response.setStatus(401);
            response.getWriter().write("{\"data\": \"token expired\"}");
            response.getWriter().flush();
            return;
        }
	} 
} 
