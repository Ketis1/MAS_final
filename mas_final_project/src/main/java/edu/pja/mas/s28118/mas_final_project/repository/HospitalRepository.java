package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Hospital;
import edu.pja.mas.s28118.mas_final_project.model.PublicHospital;
import edu.pja.mas.s28118.mas_final_project.model.PrivateHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    @Query("SELECT h FROM Hospital h WHERE h.name = :name")
    Optional<Hospital> findByName(@Param("name") String name);

    @Query("SELECT h FROM Hospital h JOIN h.phoneNumbers p WHERE p = :phoneNumber")
    List<Hospital> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    

    @Query("SELECT h FROM Hospital h JOIN h.publicHospital p WHERE p.regonNumber = :regonNumber")
    Optional<Hospital> findByPublicHospitalRegonNumber(@Param("regonNumber") String regonNumber);
    

    @Query("SELECT h FROM Hospital h WHERE SIZE(h.rooms) > :minRooms")
    List<Hospital> findByRoomsCountGreaterThan(@Param("minRooms") int minRooms);


    @Query("SELECT h FROM Hospital h WHERE h.address.city = :city AND SIZE(h.rooms) >= :minRooms")
    List<Hospital> findByCityAndMinRooms(@Param("city") String city, @Param("minRooms") int minRooms);
    

}