package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    @Query("SELECT p FROM Prescription p WHERE p.prescriptionCode = :prescriptionCode")
    Optional<Prescription> findByPrescriptionCode(@Param("prescriptionCode") String prescriptionCode);

    @Query("SELECT p FROM Prescription p WHERE p.patient.id = :patientId AND p.issueDate BETWEEN :startDate AND :endDate")
    List<Prescription> findByPatientIdAndIssueDateBetween(
            @Param("patientId") Long patientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Prescription p WHERE p.expiryDate < :date")
    List<Prescription> findByExpiryDateBefore(@Param("date") LocalDate date);
}