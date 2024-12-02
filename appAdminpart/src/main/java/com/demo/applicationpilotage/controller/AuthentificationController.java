package com.demo.applicationpilotage.controller;

import com.demo.applicationpilotage.User;
import com.demo.applicationpilotage.service.AuthentificationService;
import com.demo.applicationpilotage.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthentificationController {

    @Autowired
    private AuthentificationService authentificationService;

    @Autowired
    private UserRepository userRepository;  // Injecter le UserRepository

    // Login
    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        if (loginRequest.getPassword().isEmpty()) {
            return "Le mot de passe ne peut pas être vide.";
        }

        User user = authentificationService.authentifierUtilisateur(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return "Bienvenue " + user.getRole() + " : " + user.getEmail();
        } else {
            return "Identifiants invalides.";
        }
    }
    
    // Signup
    @PostMapping("/signup")
    public String signUp(@RequestBody User newUser) {
        if (newUser.getEmail().isEmpty() || newUser.getPassword().isEmpty()) {
            return "L'email ou le mot de passe ne peuvent pas être vides.";
        }

        // Vérifiez si l'email existe déjà
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return "L'email est déjà utilisé.";
        }

        // Enregistrer l'utilisateur dans la base de données
        authentificationService.createUser(newUser);
        return "\"User registered successfully. Awaiting admin approval";
    }

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return authentificationService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return authentificationService.getUserById(id);
    }
    
    @GetMapping("/users/employes")
    public List<User> getAllEmployes() {
        return authentificationService.getUsersByRole("EMPLOYE");
    }
    
    
    @GetMapping("/users/managers")
    public List<User> getAllManagers() {
       return authentificationService.getUsersByRole("MANAGER");
        
    }
    // Update user
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return authentificationService.updateUser(id, updatedUser);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        boolean isDeleted = authentificationService.deleteUser(id);
        return isDeleted ? "Utilisateur supprimé avec succès." : "Utilisateur introuvable.";
    }
    
    
    
}
