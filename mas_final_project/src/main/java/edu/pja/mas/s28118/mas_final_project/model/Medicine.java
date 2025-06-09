package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    
    @NotBlank(message = "Medicine code is mandatory")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{4}$", message = "Invalid medicine code format. Required format: AA1234")
    @Column(unique = true)
    private String medicineCode;
    
    @NotNull(message = "Requires prescription flag is mandatory")
    private Boolean requiresPrescription;


    @OneToMany(mappedBy = "medicine")
    private Set<PrescriptionItem> prescriptionItems = new HashSet<>();

    //TODO check if its needed
    @OneToMany(mappedBy = "medicine")
    @Where(clause = "is_realized = true")
    private Set<PrescriptionItem> realizedPrescriptionItems = new HashSet<>();




}
