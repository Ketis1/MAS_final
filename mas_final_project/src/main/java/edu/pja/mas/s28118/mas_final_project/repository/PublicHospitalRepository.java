package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.PublicHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicHospitalRepository extends JpaRepository<PublicHospital, Long> {
    @Query("SELECT p FROM PublicHospital p WHERE p.regonNumber = :regonNumber")
    Optional<PublicHospital> findByRegonNumber(@Param("regonNumber") String regonNumber);
}