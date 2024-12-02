package com.demo.applicationpilotage.service;

import com.demo.applicationpilotage.User;
import com.demo.applicationpilotage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ProfileService {
	 @Autowired
	    private UserRepository userRepository;
	
	    public User updateUserProfile(Long userId, User updatedProfile) {
	        Optional<User> userOpt = userRepository.findById(userId);
	        if (userOpt.isPresent()) {
	            User existingUser = userOpt.get();

	            
	            existingUser.setFirstName(updatedProfile.getFirstName());
	            existingUser.setLastName(updatedProfile.getLastName());

	            return userRepository.save(existingUser);
	        }
	        return null; 
	    }

	    
	    public User getUserProfile(Long userId) {
	        return userRepository.findById(userId).orElse(null);
	    }
	
}





   

   