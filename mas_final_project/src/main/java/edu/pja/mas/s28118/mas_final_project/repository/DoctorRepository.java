package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d WHERE d.licenseNumber = :licenseNumber")
    Optional<Doctor> findByLicenseNumber(@Param("licenseNumber") String licenseNumber);

    @Query("SELECT d FROM Doctor d JOIN d.doctorSpecializations ds WHERE ds.specialization.specializationName = :specializationName")
    List<Doctor> findBySpecializationName(String specializationName);

}