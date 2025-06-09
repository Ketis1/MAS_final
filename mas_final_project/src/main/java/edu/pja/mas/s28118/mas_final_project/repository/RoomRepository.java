package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Doctor;
import edu.pja.mas.s28118.mas_final_project.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.floor = :floor")
    List<Room> findByFloor(@Param("floor") Integer floor);

    @Query("SELECT r FROM Room r WHERE r.doctor = :doctor")
    List<Room> findByDoctor(@Param("doctor") Doctor doctor);

    @Query("SELECT r FROM Room r WHERE r.floor = :floor AND r.roomNumber = :roomNumber")
    Optional<Room> findByFloorAndRoomNumber(@Param("floor") Integer floor, @Param("roomNumber") Integer roomNumber);

    @Query("SELECT r FROM Room r WHERE r.floor = :floor ORDER BY r.roomNumber ASC")
    List<Room> findByFloorOrderByRoomNumberAsc(@Param("floor") Integer floor);
}