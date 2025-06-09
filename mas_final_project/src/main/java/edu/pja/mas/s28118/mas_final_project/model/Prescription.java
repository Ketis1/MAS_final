package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prescription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull(message = "Issue date is mandatory")
    private LocalDate issueDate;
    
    @NotNull(message = "Expiry date is mandatory")
    private LocalDate expiryDate;
    
    @NotNull(message = "Prescription code is mandatory")
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{4}$", message = "Invalid prescription code format. Required format: XXXX-XXXX-XXXX")
    private String prescriptionCode;

    @NotNull(message = "Patient is mandatory")
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "prescription", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PrescriptionItem> prescriptionItems = new HashSet<>();

    //TODO check if its proper way of implementing subset
    @OneToMany(mappedBy = "prescription")
    @Where(clause = "is_realized = true")
    private Set<PrescriptionItem> realizedItems = new HashSet<>();

//    @Transient
//    public Set<PrescriptionItem> getRealizedItems() {
//        return prescriptionItems.stream()
//                .filter(PrescriptionItem::getIsRealized)
//                .collect(java.util.stream.Collectors.toSet());
//    }


}