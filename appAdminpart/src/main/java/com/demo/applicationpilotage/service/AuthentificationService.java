package com.demo.applicationpilotage.service;


import com.demo.applicationpilotage.User;

import com.demo.applicationpilotage.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthentificationService {

    @Autowired
    private UserRepository userRepository;

    // Authentification d'un utilisateur
    public User authentifierUtilisateur(String email, String password) {
        // Vérifier si l'email existe dans la base de données
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return null; // Utilisateur non trouvé
        }

        // Si l'email existe, récupérer l'utilisateur
        User user = userOpt.get();

        // Vérifier si le mot de passe est correct
        if (user.getPassword().equals(password)) {
            return user; // Authentification réussie
        }

        // Si le mot de passe ne correspond pas
        return null; // Mot de passe incorrect
    }

    // Récupérer tous les utilisateurs
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer les utilisateurs par rôle
    public List<User> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(role);
        System.out.println("Utilisateurs récupérés : " + users); // Ajoutez un log pour vérifier
        return users;
    }
    
    
  
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null); // Retourne l'utilisateur ou null si non trouvé
    }
 

    // Mettre à jour un utilisateur
    public User updateUser(Long id, User updatedUser) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setRole(updatedUser.getRole());
            return userRepository.save(existingUser);
        }
        return null; // Utilisateur introuvable
    }

    // Supprimer un utilisateur par ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false; // Utilisateur introuvable
    }
    
    // Créer un nouvel utilisateur
    public User createUser(User newUser) {
    	newUser.setEnable(false);
        return userRepository.save(newUser);
    }
    
    
    public List<User> getPendingUsers() {
        return userRepository.findByEnabled(false);
    }
    
 

	public User enableUser(Long id) {
		 com.demo.applicationpilotage.User user = userRepository.findById(id).orElse(null);
	        if (user != null) {
	            user.setEnable(true);
	            userRepository.save(user);
		
	}
	        return user;
	}}