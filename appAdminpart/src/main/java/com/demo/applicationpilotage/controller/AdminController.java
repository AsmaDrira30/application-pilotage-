package com.demo.applicationpilotage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.applicationpilotage.User;
import com.demo.applicationpilotage.repository.UserRepository;
import com.demo.applicationpilotage.service.AuthentificationService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	 @Autowired
	     AuthentificationService userService;

	 
	    @Autowired
	    private UserRepository userrepository; 
	   
	    @PreAuthorize("hasRole('ADMIN')")
	    @GetMapping("/pending-users")
	    public List<User> getPendingUsers() {
	       
	        return userService.getPendingUsers();
	    }
	    @PreAuthorize("hasRole('ADMIN')")
	 @PutMapping("/enable-user/{id}")
	 public User enableUser(@PathVariable Long id) {
		 return userService.enableUser(id);
	         
	    }
	    @GetMapping("/users")
	    public List<User> getAllUsers() {
	        return userService.getAllUsers();
	    }
	    @GetMapping("/users/employes")
	    public List<User> getAllEmployes() {
	        return userService.getUsersByRole("EMPLOYE");
	    }
	    @GetMapping("/users/managers")
	    public List<User> getAllManagers() {
	       return userService.getUsersByRole("MANAGER");
	        
	    }
}
