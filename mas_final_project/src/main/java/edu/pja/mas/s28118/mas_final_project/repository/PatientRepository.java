package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Patient;
import edu.pja.mas.s28118.mas_final_project.model.Patient.InsuranceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.insuranceType = :insuranceType")
    List<Patient> findByInsuranceType(@Param("insuranceType") InsuranceType insuranceType);
}