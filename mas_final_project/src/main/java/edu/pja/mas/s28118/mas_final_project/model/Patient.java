package edu.pja.mas.s28118.mas_final_project.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Patient extends Person {
    public enum InsuranceType {
        basic,
        advanced,
        premium
    }


    @NotNull(message = "Insurance type is mandatory")
    @Enumerated(EnumType.STRING)  // Enums będą przechowywane jako String w bazie danych
    private InsuranceType insuranceType;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("date DESC")
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("issueDate DESC")
    private List<Prescription> prescriptions = new ArrayList<>();

    public List<Appointment> showAppointments() {
        return new ArrayList<>(appointments);
    }

    public Appointment bookAnAppointment(Doctor doctor, LocalDate date) {
        if (doctor == null) {
            throw new IllegalArgumentException("Doctor cannot be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(this);
        appointment.setDate(date);
        appointment.setAppointmentStatus(Appointment.AppointmentStatus.SCHEDULED);

        // Dodajemy do list pacjenta i doktora (utrzymanie spójności relacji)
        this.appointments.add(appointment);
        doctor.getAppointments().add(appointment);

        return appointment;
    }


}
