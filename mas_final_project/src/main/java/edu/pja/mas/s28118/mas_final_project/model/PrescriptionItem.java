package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Dosage is mandatory")
    @Size(min = 1, max = 255)
    private String dosage;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1,message = "Quantity must be greater than 1" )
    private Integer quantity;

    @NotBlank(message = "Doctor instructions are mandatory")
    private String doctorInstructions;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prescription_id", nullable = false)
    private Prescription prescription;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    private Boolean isRealized = false;

    public void realizeItem() {
        if (Boolean.TRUE.equals(isRealized)) {
            throw new IllegalStateException("This prescription item has already been realized.");
        }

        if (prescription.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot realize item: prescription has expired.");
        }

        this.isRealized = true;
    }
}