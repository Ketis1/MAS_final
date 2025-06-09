package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;



@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee extends Person {


    @NotNull(message = "Employment date is mandatory")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate employmentDate;

    @PositiveOrZero(message = "Salary must be a positive value")
    private Double salary;

    public static double MIN_SALARY = 3600.0;

    @AssertTrue(message = "Salary must not be less than the minimum salary")
    public boolean isSalaryAboveMinimum() {
        return salary == null || salary >= MIN_SALARY;
    }


    public abstract double calculateSalary(double months);

}
