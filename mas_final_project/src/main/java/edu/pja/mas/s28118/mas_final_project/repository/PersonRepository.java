package edu.pja.mas.s28118.mas_final_project.repository;

import edu.pja.mas.s28118.mas_final_project.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p WHERE p.lastName = :lastName")
    List<Person> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT p FROM Person p WHERE p.pesel = :pesel")
    Optional<Person> findByPesel(@Param("pesel") String pesel);
}
