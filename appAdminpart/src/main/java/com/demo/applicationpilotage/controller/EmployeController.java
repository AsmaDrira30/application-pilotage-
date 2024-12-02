package com.demo.applicationpilotage.controller;




import com.demo.applicationpilotage.Employe;
import com.demo.applicationpilotage.User;
import com.demo.applicationpilotage.service.EmployeService;
import jakarta.validation.Valid; // or jakarta.validation.Valid for newer versions.

import com.demo.applicationpilotage.service.AuthentificationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/employes")
public class EmployeController {

    @Autowired
    private EmployeService employeService;

    @Autowired
    private AuthentificationService authentificationService;

    @PostMapping
    public ResponseEntity<?> createEmploye(@RequestBody @Valid Employe employe) { 
        try {
            // Vérification de la validité de l'employé
            if (employe == null || employe.getUser() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Données de l'employé manquantes.");
            }

            // Récupérer l'utilisateur associé à l'employé
            User user = authentificationService.getUserById(employe.getUser().getId());
 // Ensure this method fetches the user correctly.

            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utilisateur introuvable.");
            }

            // Assigner l'utilisateur à l'employé
            employe.setUser(user);

            // Sauvegarder l'employé
            Employe savedEmploye = employeService.createEmploye(user, employe);

            // Retourner l'employé créé avec un code 201
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmploye);

        } catch (Exception e) {
            // Gérer les exceptions générales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la création de l'employé.");
        }
    }

    
 // Récupérer tous les employés
    @GetMapping
    public ResponseEntity<?> getAllEmployes() {
        try {
            List<Employe> employes = employeService.getAllEmployes();  // Appel du service pour récupérer les employés
            if (employes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun employé trouvé.");
            }
            return ResponseEntity.status(HttpStatus.OK).body(employes);  // Retourner les employés trouvés
        } catch (Exception e) {
    
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération des employés.");
        }
    }
    // Récupérer un employé par ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeById(@PathVariable Long id) {
        try {
            Employe employe = employeService.getEmployeById(id);
            if (employe == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé non trouvé.");
            }
            return ResponseEntity.ok(employe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la récupération de l'employé.");
        }
    }

    // Mise à jour d'un employé
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmploye(@PathVariable Long id, @RequestBody @Valid Employe employeDetails) {
        try {
            Employe updatedEmploye = employeService.updateEmploye(id, employeDetails);
            if (updatedEmploye == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé non trouvé.");
            }
            return ResponseEntity.ok(updatedEmploye);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la mise à jour de l'employé.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmploye(@PathVariable Long id) {
        try {
            System.out.println("Tentative de suppression de l'employé avec ID : " + id);
            boolean isDeleted = employeService.deleteEmployeById(id);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.OK).body("Suppression effectuée avec succès.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employé introuvable.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la suppression.");
        }
    }

}