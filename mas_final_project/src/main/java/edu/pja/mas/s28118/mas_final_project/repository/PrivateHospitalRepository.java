package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.PrivateHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateHospitalRepository extends JpaRepository<PrivateHospital, Long> {
    @Query("SELECT p FROM PrivateHospital p WHERE :fundingSource MEMBER OF p.fundingSources")
    List<PrivateHospital> findByFundingSourcesContaining(@Param("fundingSource") String fundingSource);

}