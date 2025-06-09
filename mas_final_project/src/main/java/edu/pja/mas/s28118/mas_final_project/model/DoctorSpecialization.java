package edu.pja.mas.s28118.mas_final_project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSpecialization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "specialization_id", nullable = false)
    private Specialization specialization;

    @NotNull(message = "Issue date is mandatory")
    @PastOrPresent
    private LocalDate issueDate;

    @NotBlank(message = "Issuing authority is mandatory")
    @Size(min = 2, max = 255)
    private String issuingAuthority;

}
