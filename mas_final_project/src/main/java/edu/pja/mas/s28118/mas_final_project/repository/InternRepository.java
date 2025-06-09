package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Doctor;
import edu.pja.mas.s28118.mas_final_project.model.Intern;
import edu.pja.mas.s28118.mas_final_project.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternRepository extends JpaRepository<Intern, Long> {
    @Query("SELECT i FROM Intern i WHERE i.mentoringDoctor = :doctor")
    List<Intern> findByMentoringDoctor(@Param("doctor") Doctor doctor);

    @Query("SELECT i FROM Intern i WHERE i.supervisingNurse = :nurse")
    List<Intern> findBySupervisingNurse(@Param("nurse") Nurse nurse);

    @Query("SELECT i FROM Intern i WHERE i.isVoluntary = :isVoluntary")
    List<Intern> findByIsVoluntary(@Param("isVoluntary") boolean isVoluntary);

}