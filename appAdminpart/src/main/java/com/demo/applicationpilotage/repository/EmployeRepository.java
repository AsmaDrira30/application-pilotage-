package com.demo.applicationpilotage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.applicationpilotage.Employe;
public interface EmployeRepository extends JpaRepository<Employe, Long> {

}
