package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    @Query("SELECT n FROM Nurse n WHERE n.licenseNumber = :licenseNumber")
    Optional<Nurse> findByLicenseNumber(@Param("licenseNumber") String licenseNumber);

    @Query("SELECT n FROM Nurse n WHERE n.shiftType = :shiftType")
    List<Nurse> findByShiftType(@Param("shiftType") Nurse.ShiftType shiftType);

    @Query("SELECT n FROM Nurse n WHERE n.salary > :salary")
    List<Nurse> findBySalaryGreaterThan(@Param("salary") Double salary);
}