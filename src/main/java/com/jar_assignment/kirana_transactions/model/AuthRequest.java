package com.jar_assignment.kirana_transactions.model;

import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest { 

	private String username; 
	private String password; 

}
