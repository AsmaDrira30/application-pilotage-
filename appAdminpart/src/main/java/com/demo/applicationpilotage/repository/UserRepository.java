package com.demo.applicationpilotage.repository;

import com.demo.applicationpilotage.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode pour trouver un utilisateur par email
    Optional<User> findByEmail(String email);
    List<User> findByRole(String role);
    List<User> findByEnabled(boolean enabled); 
    
}
