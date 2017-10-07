package com.sravan.businessconnect.todo.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class UsernameAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 
	     if (authentication == null || !authentication.isAuthenticated()) {
	         return null;
	     }
	 
	     return ((User) authentication.getPrincipal()).getUsername();
	}

}

/*
 * 
 
Getting the Information of the Authenticated User

The auditing infrastructure of Spring Data JPA uses the AuditorAware<T> interface when it 
needs to get the information of the authenticated user. The AuditorAware interface has one 
type parameter (T) which describes the type of the entity’s field that contains the auditing 
information.

Because we have to create a class that returns the username of the authenticated user, 
we have to follow these steps:

Create UsernameAuditorAware class and implement the AuditorAware interface. Because we wa
nt to store the username of the authenticated user (String), we must set the value of the 
type parameter to String.

Implement the getCurrentAuditor() method by following these steps:
Get an Authentication object from the SecurityContext.
Return null if the authentication is not found or the found authentication is not 
authenticated.

Return the username of the authenticated user.

*/