package com.jar_assignment.kirana_transactions.service;


import com.jar_assignment.kirana_transactions.model.User; 
import com.jar_assignment.kirana_transactions.repository.UserRepo; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service; 

import java.util.Optional; 

@Service
public class UserInfoService implements UserDetailsService { 

	@Autowired
	private UserRepo repository; 

	@Autowired
	private PasswordEncoder encoder; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
		Optional<User> userDetail = repository.findByUsername(username); 
		return userDetail.map(UserInfoDetails::new) 
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
	} 

	public String addUser(User userInfo) { 
		userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
		repository.save(userInfo); 
		return "User Added Successfully"; 
	} 

    public User findbyUsername(String username) {
        Optional<User> optionalUser = repository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

} 
