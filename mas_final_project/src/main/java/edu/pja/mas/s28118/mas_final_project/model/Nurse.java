package edu.pja.mas.s28118.mas_final_project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;


@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("NURSE")
public class Nurse extends Employee {
    public enum ShiftType{
        DAY,
        NIGHT
    }

    @NotBlank(message = "License number is mandatory")
    @Pattern(regexp = "^(P-)?\\d{6}$", message = "Invalid nurse license number (PWZ)")
    @Column(unique = true)
    private String licenseNumber;

    @Enumerated(EnumType.STRING)
    private ShiftType shiftType;


    public static final double NIGHT_SHIFT_BONUS = 500.0;

    @OneToMany(mappedBy = "supervisingNurse")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Intern> supervisedInterns = new HashSet<>();

    @Override
    public double calculateSalary(double months) {
        if (shiftType == ShiftType.NIGHT) {
            return this.getSalary()*months + NIGHT_SHIFT_BONUS;
        }
        else {
            return this.getSalary()*months;
        }
    }
}
