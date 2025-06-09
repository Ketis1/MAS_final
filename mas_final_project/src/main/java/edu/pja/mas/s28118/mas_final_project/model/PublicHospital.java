package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicHospital {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @NotNull(message = "REGON number is mandatory")
    @Pattern(regexp = "^[0-9]{9}$", message = "REGON number must be exactly 9 digits")
    @Column(unique = true, nullable = false)
    private String regonNumber;






}