package edu.pja.mas.s28118.mas_final_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("INTERN")
public class Intern extends Employee {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate internshipEndDate;

    private boolean isVoluntary;

    @AssertTrue(message = "Internship end date must be after hire date.")
    public boolean isInternshipEndDateAfterHireDate() {
        return internshipEndDate == null || getEmploymentDate() == null || !internshipEndDate.isBefore(getEmploymentDate());
    }

    @AssertTrue(message = "Salary must be 0 for voluntary intership")
    public boolean isActuallyVoluntary() {
        if (isVoluntary) {
            return getSalary() == 0;
        }else {
            return true;
        }
    }

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor mentoringDoctor;

    @ManyToOne
    @JoinColumn(name = "nurse_id")
    private Nurse supervisingNurse;

    @AssertTrue(message = "Intern must be supervised either by a doctor or a nurse, but not both")
    private boolean isValidSupervisor() {
        return (mentoringDoctor != null && supervisingNurse == null) ||
                (mentoringDoctor == null && supervisingNurse != null);
    }

    @PrePersist
    @PreUpdate
    private void validateXOR() {
        if ((mentoringDoctor == null && supervisingNurse == null) ||
                (mentoringDoctor != null && supervisingNurse != null)) {
            throw new IllegalStateException("Intern must be supervised either by a doctor or a nurse, but not both.");
        }
    }

    @Override
    public double calculateSalary(double months) {
        if (isVoluntary) {
            return 0;
        }
        else {
            return getSalary()*months;
        }
    }
}
