package com.jar_assignment.kirana_transactions.controller;

import com.jar_assignment.kirana_transactions.model.AuthRequest; 
import com.jar_assignment.kirana_transactions.model.User; 
import com.jar_assignment.kirana_transactions.service.JwtService; 
import com.jar_assignment.kirana_transactions.service.UserInfoService;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*; 

@RestController
@RequestMapping("/user") 
public class UserController { 

	@Autowired
	private UserInfoService service; 

	@Autowired
	private JwtService jwtService; 

	@Autowired
	private AuthenticationManager authenticationManager; 

	@PostMapping("/add") 
	public ResponseEntity<?> addNewUser(@RequestBody User userInfo) { 
        try {
            userInfo.setRegisteredAt(LocalDateTime.now());
            service.addUser(userInfo);
            return ResponseEntity.ok().body(Map.of("data", "success"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("data","An error occured while creating user"));
        }
	} 

	@PostMapping("/generateToken") 
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
            if (authentication.isAuthenticated()) { 
                return ResponseEntity.ok().body(Map.of("data", jwtService.generateToken(authRequest.getUsername())));
            } else { 
                return ResponseEntity.badRequest().body(Map.of("data","user not found"));
            } 
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("data", "An error occured while creating token"));
        }
		
	} 

} 
