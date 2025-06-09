package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    @Query("SELECT m FROM Medicine m WHERE m.medicineCode = :medicineCode")
    Optional<Medicine> findByMedicineCode(@Param("medicineCode") String medicineCode);

    @Query("SELECT m FROM Medicine m WHERE m.requiresPrescription = :requiresPrescription")
    List<Medicine> findByRequiresPrescription(@Param("requiresPrescription") Boolean requiresPrescription);
}