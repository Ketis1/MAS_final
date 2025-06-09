package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long> {
    @Query("SELECT pi FROM PrescriptionItem pi WHERE pi.prescription.id = :prescriptionId")
    List<PrescriptionItem> findByPrescriptionId(@Param("prescriptionId") Long prescriptionId);

    @Query("SELECT pi FROM PrescriptionItem pi WHERE pi.isRealized = :isRealized")
    List<PrescriptionItem> findByIsRealized(@Param("isRealized") Boolean isRealized);

    @Query("SELECT pi FROM PrescriptionItem pi WHERE pi.medicine.id = :medicineId")
    List<PrescriptionItem> findByMedicineId(@Param("medicineId") Long medicineId);
}