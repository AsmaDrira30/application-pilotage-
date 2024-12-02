package com.demo.applicationpilotage.service;
import com.demo.applicationpilotage.Employe;
import com.demo.applicationpilotage.User;
import com.demo.applicationpilotage.repository.EmployeRepository;
import com.demo.applicationpilotage.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeRepository employeRepository;

    // Method to create and save a new employee
    public Employe createEmploye(User user, Employe employeDetails) {
        if (user.getId() == null) {
            // Save user first
            User savedUser = userRepository.save(user);
            employeDetails.setUser(savedUser); // Link the employee to the saved user
        } else {
            // If user already has an ID, just set it on the employee
            employeDetails.setUser(user);
        }

        return employeRepository.save(employeDetails); // Save the employee
    }
    public List<Employe> getAllEmployes() {
        try {
            return employeRepository.findAll();  // Récupérer tous les employés depuis la base de données
        } catch (Exception e) {
            e.printStackTrace();  // Afficher l'exception complète dans les logs
            throw new RuntimeException("Erreur lors de la récupération des employés: " + e.getMessage());  // Inclure le message d'erreur dans l'exception
        }
    }
public Employe getEmployeById(Long id) {
    return employeRepository.findById(id).orElse(null);
}

public Employe updateEmploye(Long id, Employe employeDetails) {
    Optional<Employe> optionalEmploye = employeRepository.findById(id);
    if (optionalEmploye.isPresent()) {
        Employe existingEmploye = optionalEmploye.get();
        existingEmploye.setNom(employeDetails.getNom());
        existingEmploye.setPrenom(employeDetails.getPrenom());
        existingEmploye.setPoste(employeDetails.getPoste());
        return employeRepository.save(existingEmploye);
    }
    return null;
}

public boolean deleteEmployeById(Long id) {
    if (employeRepository.existsById(id)) {
        System.out.println("Suppression de l'employé avec l'ID : " + id); // Log avant suppression
        employeRepository.deleteById(id);
        System.out.println("Employé supprimé avec succès."); // Log après suppression
        return true;
    }
    System.out.println("Employé introuvable avec l'ID : " + id); // Log si non trouvé
    return false;
}
}