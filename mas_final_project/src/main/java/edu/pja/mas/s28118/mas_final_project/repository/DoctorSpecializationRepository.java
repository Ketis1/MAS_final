package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.DoctorSpecialization;
import edu.pja.mas.s28118.mas_final_project.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Long> {
    @Query("SELECT ds FROM DoctorSpecialization ds WHERE ds.doctor.id = :doctorId")
    List<DoctorSpecialization> findByDoctorId(@Param("doctorId") Long doctorId);

}

